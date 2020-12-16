package com.moa.coroutines.global

import com.moa.coroutines.models.Response
import com.moa.coroutines.models.getServerResponse
import com.moa.coroutines.models.res
import com.moa.coroutines.models.serverError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait

/**
 * @author omar Dec 11, 2020
 */

/**
 * Global Variables
 */

const val authorId = "authorId"

const val bookId = "bookId"


/**
 * Global Functions
 */
// #Summary : method used to execute a concurrent job and catch any occurring error
suspend inline fun handle(contentType: MediaType = APPLICATION_JSON, job: () -> Any): ServerResponse {
    return try {
        // #Create a Successful Server Response
        // -> When no Server error has occurred
        ok()
            .contentType(contentType)
            .bodyValueAndAwait(job().res)
    } catch (ex: Exception) {
        ex.getServerResponse()
    }
}

// #Summary : method used to execute a concurrent job that return a flow and catch any occurring error
// -> Handling flows are bit different than normal return since we need to `collect` the flow
// and convert it into the respective result instead of normal conversion
suspend inline fun <reified T> handleFlow(contentType: MediaType = APPLICATION_JSON, job: () -> Flow<T>): ServerResponse {
    return try {
        // #Create a Successful Server Response
        // -> When no Server error has occurred
        mutableListOf<T>().run {
            ok()
                .contentType(contentType)
                .bodyValueAndAwait(
                    // #Execute the job
                    job()
                        // #Collect and map the flow to a collection list
                        .toCollection(this)
                        // #Convert the list to @Response::class
                        .res
                )
        }
    } catch (ex: Exception) {
        ex.getServerResponse()
    }
}


// #Summary : extension method used to return a String from a nullable String
// -> the method take a default value that will replace the null String
fun String?.value(defaultValue: String = "") = this ?: defaultValue

