package com.moa.coroutines.interfaces

import com.moa.coroutines.dto.BookDto
import com.moa.coroutines.models.ServerError
import com.moa.coroutines.models.base.Book
import kotlinx.coroutines.flow.Flow

/**
 * @author omar Dec 11, 2020
 */
interface IBookService : BaseInterface<Book> {
    /** Authorizations Methods  **/
    @Throws(ServerError::class)
    suspend fun validateBookName(name: String)

    /** Base Methods **/
    // #Summary method used to create a policy
    // -> This method return ServerError if :
    // * the name already exists in the database
    suspend fun createBook(bookDto: BookDto): Book

    suspend fun updateBook(bookId: String, bookDto: BookDto): Book

    suspend fun getBooksByAuthor(authorId: String): Flow<Book>
}