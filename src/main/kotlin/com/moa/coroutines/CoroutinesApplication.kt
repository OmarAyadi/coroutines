package com.moa.coroutines

import com.moa.coroutines.config.authorBeans
import com.moa.coroutines.config.beans
import com.moa.coroutines.config.bookBeans
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoroutinesApplication

fun main(args: Array<String>) {
    runApplication<CoroutinesApplication>(*args) {
        // #Initialize project beans when the application is up and running
        addInitializers(
            beans(),
            authorBeans(),
            bookBeans()
        )
    }
}
