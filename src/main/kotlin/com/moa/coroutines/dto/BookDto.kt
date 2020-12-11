package com.moa.coroutines.dto

import com.moa.coroutines.global.isNotBlank
import com.moa.coroutines.global.value
import com.moa.coroutines.models.ServerError
import com.moa.coroutines.models.base.Book

/**
 * @author omar Dec 11, 2020
 */
class BookDto(
    val authorId: String?,
    val name: String?,
    val nbPages: Int
) {
    // #Method used to validate the author
    @Throws(ServerError::class)
    inline fun isValid() {
        // #Validate author information
        // -> Scope method used to reduce ref calls
        this.run {
            authorId.isNotBlank("author id")
            name.isNotBlank("name")
        }
    }

    // #Method used to convert a BookSto instance to a valid
    // Book instance
    inline fun convert(): Book {
        // #Ref the dto and return a new Author instance
        return this.run {
            Book(
                name = name.value(),
                nbPages = nbPages
            )
        }
    }
}