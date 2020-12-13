package com.moa.coroutines.mocks

import com.moa.coroutines.models.base.Author

/**
 * @author omar Oct 23, 2020
 */

fun mockAuthor() = Author(
    firstName = "mocked first name",
    lastName = "mocked last name",
    email = "mocked email",
)