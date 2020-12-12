package com.moa.coroutines.models

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.server.ResponseStatusException

/**
 * @author omar Dec 11, 2020
 */

// `ServerError` is an Exception representation of an error that occurred, it
// describe a breaking point from api execution.
//
// `ServerError` provide support to status handling such as Forbidden, Bad_Request ...
// ###  Example : BadRequest
// ** Old Behavior
// 1. user provide an id
// 2. check if the id is valid with if else block
// 3. return an object as null and test it at the handler
// 4. convert the error to Server Response
//
//
// ** With Server Error
// 0. Create a validation method that raise ServerError if the id is invalid
// 1. User provide an id
// 2. Check the validity of the id
// 3. Catch the exception at the end of the API and convert it to *Server Response*
//
class ServerError(
    override val message: String,
    val httpStatus: HttpStatus
) :
    ResponseStatusException(httpStatus, message)


// #Extention values that define ServerError types
val String.badRequest: ServerError
    get() = ServerError(this, BAD_REQUEST)

val String.forbidden: ServerError
    get() = ServerError(this, FORBIDDEN)

// #Summary : method used to validate a predicate and raise
// a bad request server error if the condition is not valid
fun badRequest(
    condition: Boolean = true,
    message: String
) {
    if (condition)
        throw message.badRequest
}


// #Summary : method used to validate a predicate and raise
// a bad request server error if the condition is not valid
fun forbidden(
    condition: Boolean = true,
    message: String
) {
    if (condition)
        throw message.forbidden
}

// `serverError` is a representation of Exception conversion
// -> this value will return a ServerError from any exception that occurred on server
// Exp :
// ServerError occurred because of an invalid id -> serverError will return the same instance
// NullPointerException occurred -> it will be converted into ServerResponse (<exception-message>,INTERNAL_SERVER_ERROR)
val Exception.serverError: ServerError
    get() = when (this) {
        is ServerError -> this
        is ResponseStatusException -> ServerError(message, status)
        else -> ServerError(message ?: "an error has occurred", INTERNAL_SERVER_ERROR)
    }


// #Summart : method used to extract ServerResponse from any occurred exception
suspend fun Exception.getServerResponse(): ServerResponse = run {
    // #Convert the exception to the respective
    this.serverError
        // #Scope : we use run scope to reduce variables initialization
        // instead of creating a serverError instance
        // -> we scope the instance and call the instance with this
        // -> PS inside `run` we can ignore `this` and call attributes
        // its attributes and methods directly
        .run {
            // #Convert the server error to a valid server response
            ServerResponse.status(
                httpStatus
            )
                // #Set the content type as JSON, since @Response
                // is a JSON representation of server return
                .contentType(
                    MediaType.APPLICATION_JSON
                )
                // #Update response body
                // -> we await the body since the response is based on a
                // coroutine scope
                .bodyValueAndAwait(
                    Response<Any>(
                        error = message
                    )
                )
        }
}