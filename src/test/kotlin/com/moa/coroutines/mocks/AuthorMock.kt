package com.moa.coroutines.mocks

import com.moa.coroutines.global.invalid
import com.moa.coroutines.models.badRequest
import com.moa.coroutines.models.base.Author
import com.moa.coroutines.svc.AuthorService
import io.mockk.coEvery
import io.mockk.mockk

/**
 * @author omar Oct 23, 2020
 */

const val validAuthorId = "valid author id"

fun mockAuthor() = Author(
    firstName = "mocked first name",
    lastName = "mocked last name",
    email = "mocked email",
)

fun mockAuthorService() =
    mockk<AuthorService>() {
        // #Prepare mocked functions
        coEvery {
            validateId(invalid)
        } throws  (invalid("author id", invalid).badRequest)

        coEvery {
            validateId(validAuthorId)
        } returns (
                mockAuthor().apply { id = validAuthorId }
                )
    }