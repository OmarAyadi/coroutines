package com.moa.coroutines.svc

import com.moa.coroutines.dto.BookDto
import com.moa.coroutines.global.exists
import com.moa.coroutines.global.invalid
import com.moa.coroutines.global.successful
import com.moa.coroutines.global.value
import com.moa.coroutines.interfaces.IAuthorService
import com.moa.coroutines.interfaces.IBookService
import com.moa.coroutines.models.badRequest
import com.moa.coroutines.models.base.Book
import com.moa.coroutines.models.forbidden
import com.moa.coroutines.repos.BookRepo
import kotlinx.coroutines.flow.Flow
import java.rmi.ServerError

/**
 * @author omar Dec 11, 2020
 */
class BookService(
    private val bookRepo: BookRepo,
    private val authorService: IAuthorService
) : IBookService {


    /**
     * Authorization methods -->
     */

    // #Summary method used to check if the author id is valid
    @Throws(ServerError::class)
    override suspend fun validateId(id: String): Book = id.run {
        // #Fetch the book from the database by the given id
        bookRepo.findById(this) ?:
        // #This block will only execute if there is no data for the given id
        throw invalid("book id", id).forbidden

    }

    // #Summary method used to check if there is another book in the database
    // we the same name
    @Throws(ServerError::class)
    override suspend fun validateBookName(name: String) {
        // #fetch the book by name
        bookRepo.findByName(name)?.let {
            // #This block will only execute if the repo hold data for the same given name
            throw exists("Book name", name).badRequest
        }
    }


    /**
     * Base methods -->
     */
    override suspend fun createBook(bookDto: BookDto): Book = run {
        // #Prepare the book
        val book = bookDto.run {
            // #Validate the dto
            isValid()
            // #Check that no other book have the same name
            validateBookName(name.value())

            // #Convert the dto to the respective object
            convert()
        }
        // #Fetch the author
        authorService.validateId(
            // #Value() is used directly since the `bookDto`
            // is already validated
            bookDto.authorId.value()
        ).run {

            // #Update book author ref
            book.author = this
        }

        // #Save the book
        bookRepo.save(book)
    }

    override suspend fun updateBook(bookId: String, bookDto: BookDto): Book {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: String): String = id.run {
        // #validate the given id
        validateId(this)

        // #Delete the book
        bookRepo.deleteById(this)

        // #Return the response
        successful(value = this)
    }

    override suspend fun getAll(): Flow<Book> = bookRepo.findAll()

    override suspend fun getById(id: String): Book =
        // #Validate book id and return the fetched instance
        validateId(id)

    override suspend fun getBooksByAuthor(authorId: String): Flow<Book> =
        bookRepo.findByAuthorId(authorId)
}