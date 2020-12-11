package com.moa.coroutines.handlers

import com.moa.coroutines.dto.BookDto
import com.moa.coroutines.global.bookId
import com.moa.coroutines.global.handle
import com.moa.coroutines.interfaces.IBookService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.awaitBody

/**
 * @author omar Dec 11, 2020
 */
class BookHandler(private val bookService: IBookService) {

    // #API #Route /api/v1/books [POST]
    // #Summary method use to create book
    // #Request {json object} BookDto
    // #Success 200 {server object} Response
    suspend fun createBook(request: ServerRequest) = handle {

        request
            // #Fetch request body
            .awaitBody<BookDto>()
            // #Create a new book
            .run {
                bookService.createBook(this)
            }

    }


    // #API #Route /api/v1/books/{bookId} [PUT]
    // #Summary update a created book
    // #Request {json object} BookDto
    // #Success 200 {server object} Response
    suspend fun updateBook(request: ServerRequest) = handle {

        // #Fetch book id from path parameters
        val bookId = request.pathVariable(bookId)

        // #Fetch request body
        request.awaitBody<BookDto>()
            // #Update the book
            .run {
                bookService.updateBook(bookId, this)
            }
    }


    // #API #Route /api/v1/books/{bookId} [DELETE]
    // #Summary delete a book by id
    // #Request {json object}
    // #Success 200 {server object} Response
    suspend fun deleteBookById(request: ServerRequest) = handle {

        // #Fetch book id from path parameters
        request.pathVariable(bookId)
            // #Delete the book
            .run {
                bookService.deleteById(this)
            }

    }

    // #API #Route /api/v1/books/{bookId} [GET]
    // #Summary return a book by id
    // #Request {json object}
    // #Success 200 {server object} Response
    suspend fun getBookById(request: ServerRequest) = handle {
        // #Fetch book id from path parameters
        request.pathVariable(bookId)
            // #Fetch target book
            .run {
                bookService.getById(this)
            }
    }

    // #API #Route /api/v1/books [GET]
    // #Summary return all books
    // #Request {json object}
    // #Success 200 {server object} Response
    suspend fun getAllBooks(request: ServerRequest) = handle {
        bookService.getAll()
    }
}