package com.moa.coroutines.mocks

import com.moa.coroutines.models.base.Book

/**
 * @author omar Oct 23, 2020
 */

fun mockBook(authorId: String = generateId) = Book(
    authorId,
    name = "mocked book",
    nbPages = 20
)