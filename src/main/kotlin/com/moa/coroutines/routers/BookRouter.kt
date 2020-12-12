package com.moa.coroutines.routers

import com.moa.coroutines.global.bookId
import com.moa.coroutines.handlers.BookHandler
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

/**
 * @author omar Dec 11, 2020
 */
// #Summary
//  `bookRouter` define @Book model APIs
//  `coRouter` is the implementation of reactive router based on Coroutine
fun bookRouter(bookHandler: BookHandler) = coRouter {

    // #Map book routes to handler functions
    "/api/v1/books".nest {

        POST("", accept(APPLICATION_JSON), bookHandler::createBook)

        GET("/{$bookId}", bookHandler::getBookById)

        GET("", bookHandler::getAllBooks)

        PUT("/{$bookId}", accept(APPLICATION_JSON), bookHandler::updateBook)

        DELETE("/{$bookId}", bookHandler::deleteBookById)

    }
}