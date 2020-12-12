package com.moa.coroutines.dto

import com.moa.coroutines.global.isNotBlank
import com.moa.coroutines.global.value
import com.moa.coroutines.models.ServerError
import com.moa.coroutines.models.base.Author

/**
 * @author omar Dec 11, 2020
 */
class AuthorDto(
    val firstName: String?,
    val lastName: String?,
    val email: String?
) {
    // #Method used to validate the author
    @Throws(ServerError::class)
    inline fun isValid() {
        // #Validate author information
        // -> Scope method used to reduce ref calls
        this.run {
            firstName.isNotBlank("firstName")
            lastName.isNotBlank("lastName")
            email.isNotBlank("email")
        }
    }

    // #Method used to convert an AuthorDto instance to a valid
    // Author instance
    inline fun convert(): Author {
        // #Ref the dto and return a new Author instance
        return this.run {
            Author(
                null,
                firstName.value(),
                lastName.value(),
                email.value()
            )
        }
    }
}