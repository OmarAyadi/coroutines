package com.moa.coroutines.handlers

import com.moa.coroutines.dto.AuthorDto
import com.moa.coroutines.global.authorId
import com.moa.coroutines.global.handle
import com.moa.coroutines.interfaces.IAuthorService
import com.moa.coroutines.interfaces.IBookService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.awaitBody

/**
 * @author omar Dec 11, 2020
 */
class AuthorHandler(
    private val authorService: IAuthorService,
    private val bookService: IBookService
) {

    // #API #Route /api/v1/authors [POST]
    // #Summary method use to create author
    // #Request {json object} AuthorDto
    // #Success 200 {server object} Response
    suspend fun createAuthor(request: ServerRequest) = handle {

        // #Fetch request body
        request.awaitBody<AuthorDto>()
            // #Create a new author
            .run {
                authorService.createAuthor(this)
            }

    }


    // #API #Route /api/v1/authors/{authorId} [PUT]
    // #Summary update a created author
    // #Request {json object} AuthorDto
    // #Success 200 {server object} Response
    suspend fun updateAuthor(request: ServerRequest) = handle {

        // #Fetch author id from path parameters
        val authorId = request.pathVariable(authorId)

        // #Fetch request body
        request.awaitBody<AuthorDto>()
            // #Update the author
            .run {
                authorService.updateAuthor(authorId, this)
            }
    }


    // #API #Route /api/v1/authors/{authorId} [DELETE]
    // #Summary delete a author by id
    // #Request {json object}
    // #Success 200 {server object} Response
    suspend fun deleteAuthorById(request: ServerRequest) = handle {

        // #Fetch author id from path parameters
        request.pathVariable(authorId)
            // #Delete the author
            .run {
                authorService.deleteById(this)
            }

    }

    // #API #Route /api/v1/authors/{authorId} [GET]
    // #Summary return a author by id
    // #Request {json object}
    // #Success 200 {server object} Response
    suspend fun getAuthorById(request: ServerRequest) = handle {
        // #Fetch author id from path parameters
        request.pathVariable(authorId)
            // #Fetch target author
            .run {
                authorService.getById(this)
            }
    }

    // #API #Route /api/v1/authors [GET]
    // #Summary return all authors
    // #Request {json object}
    // #Success 200 {server object} Response
    suspend fun getAllAuthors(request: ServerRequest) = handle {
        authorService.getAll()
    }

    // #API #Route /api/v1/authors/{authorId}/books [GET]
    // #Summary return all author books
    // #Request {json object}
    // #Success 200 {server object} Response
    suspend fun getAuthorBooks(request: ServerRequest) = handle {
        // #Fetch author id from path parameters
        request.pathVariable(authorId)
            // #Fetch target author
            .run {
                bookService.getBooksByAuthor(this)
            }
    }
}