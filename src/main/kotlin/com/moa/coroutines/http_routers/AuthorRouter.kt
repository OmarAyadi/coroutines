package com.moa.coroutines.http_routers

import com.moa.coroutines.global.authorId
import com.moa.coroutines.handlers.AuthorHandler
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

/**
 * @author omar Dec 11, 2020
 */

// #Summary
//  `authorRouter` define @Author model APIs
//  `coRouter` is the implementation of reactive router based on Coroutine
fun authorRouter(authorHandle: AuthorHandler) = coRouter {

    // #Map author routes to handler functions
    "/api/v1/authors".nest {

        POST("", accept(APPLICATION_JSON), authorHandle::createAuthor)

        PUT("/{$authorId}", accept(APPLICATION_JSON), authorHandle::updateAuthor)

        DELETE("/{$authorId}", authorHandle::deleteAuthorById)

        GET("/{$authorId}", authorHandle::getAuthorById)

        GET("", authorHandle::getAllAuthors)

        GET("/{$authorId}/books", authorHandle::getAuthorBooks)
    }
}