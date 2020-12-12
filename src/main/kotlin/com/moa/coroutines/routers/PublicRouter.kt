package com.moa.coroutines.routers

import com.moa.coroutines.models.res
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.coRouter

/**
 * @author omar Dec 11, 2020
 */

// #Summary `publicRouter` define all public routes
// `coRouter` is the implementation of reactive router based on Coroutine
fun publicRouter() = coRouter {

    GET("/ping") {
        ServerResponse.ok().bodyValueAndAwait("pong".res)
    }

}