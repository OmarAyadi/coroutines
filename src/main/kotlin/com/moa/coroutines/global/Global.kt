package com.moa.coroutines.global

import com.moa.coroutines.models.Response
import com.moa.coroutines.models.res
import com.moa.coroutines.models.serverError
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.bodyValueAndAwait

/**
 * @author omar Dec 11, 2020
 */

/**
 * Global Variables
 */

val authorId = "authorId"

val bookId = "bookId"


/**
 * Global Functions
 */
// #Summary : method used to execute a concurrent job and catch any occurring error
suspend inline fun handle(job: () -> Any) = run {
    try {
        // #Create a Successful Server Response
        // -> When no Server error has occurred
        ok()
            .contentType(APPLICATION_JSON)
            .bodyValueAndAwait(job().res)
    } catch (ex: Exception) {
        // #Convert the exception to the respective
        ex.serverError
            // #Scope : we use run scope to reduce variables initialization
            // instead of creating a serverError instance
            // -> we scope the instance and call the instance with this
            // -> PS inside `run` we can ignore `this` and call attributes
            // its attributes and methods directly
            .run {
                // #Convert the server error to a valid server response
                status(
                    httpStatus
                )
                    // #Set the content type as JSON, since @Response
                    // is a JSON representation of server return
                    .contentType(
                        APPLICATION_JSON
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
}


// #Summary : extension method used to return a String from a nullable String
// -> the method take a default value that will replace the null String
inline fun String?.value(defaultValue: String = "") = this ?: defaultValue

