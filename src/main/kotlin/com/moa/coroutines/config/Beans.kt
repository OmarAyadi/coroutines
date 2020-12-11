package com.moa.coroutines.config

import com.moa.coroutines.handlers.AuthorHandler
import com.moa.coroutines.handlers.BookHandler
import com.moa.coroutines.http_routers.authorRouter
import com.moa.coroutines.http_routers.bookRouter
import com.moa.coroutines.http_routers.publicRouter
import com.moa.coroutines.svc.AuthorService
import com.moa.coroutines.svc.BookService
import org.springframework.context.support.beans

/**
 * @author omar Dec 11, 2020
 */

// #Summary
// `authorBeans` define all beans respective to @Author model
// * This DSL function is used to remove annotation based classes
fun authorBeans() = beans {
    // #Prepare @Author required beans
    bean<AuthorService>()
    bean<AuthorHandler>()

    bean {
        authorRouter(ref())
    }
}

// #Summary
// `authorBeans` define all beans respective to @Book model
// * This DSL function is used to remove annotation based classes
fun bookBeans() = beans {
    // #Prepare @Book required beans
    bean<BookService>()
    bean<BookHandler>()

    bean {
        bookRouter(ref())
    }
}

// #Summary
// `beans` define all public and extra beans
// * This DSL function is used to remove annotation based classes
fun beans() = beans {

    // #Prepare routes
    // public routes
    bean {
        publicRouter()
    }
}