package com.moa.coroutines.svc

import com.moa.coroutines.dto.AuthorDto
import com.moa.coroutines.global.invalid
import com.moa.coroutines.global.successful
import com.moa.coroutines.interfaces.IAuthorService
import com.moa.coroutines.models.base.Author
import com.moa.coroutines.models.forbidden
import com.moa.coroutines.repos.AuthorRepo
import com.moa.coroutines.repos.BookRepo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import java.rmi.ServerError

/**
 * @author omar Dec 11, 2020
 */
class AuthorService(
    private val authorRepo: AuthorRepo,
    private val bookRepo: BookRepo
) : IAuthorService {


    /**
     * Authorization methods -->
     */

    // #Summary method used to check if the author id is valid
    @Throws(ServerError::class)
    override suspend fun validateId(id: String): Author = run {
        // #Fetch the author from the database by the given id
        authorRepo.findById(id) ?:
        // #This block will only execute if there is no data for the given id
        throw invalid("author id", id).forbidden
    }


    /**
     * Base methods -->
     */
    override suspend fun createAuthor(authorDto: AuthorDto): Author = run {
        // #Ref the dto to reduce instances calls and creations
        authorDto.run {
            // #Validate the dto
            isValid()
            // #Convert the dto to the respective object
            convert()
        }.run {
            // #Create the author
            authorRepo.save(this)
        }
    }


    override suspend fun updateAuthor(authorId: String, authorDto: AuthorDto): Author {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: String): String = id.run {
        // #validate the given id
        validateId(this)

        // #Delete the author
        coroutineScope {
            authorRepo.deleteById(id)
        }

        // #Delete all books related to the author
        coroutineScope {
            bookRepo.deleteBookByAuthorId(id)
        }

        // #Return the response
        successful(value = this)
    }

    override suspend fun getAll(): Flow<Author> = authorRepo.findAll()

    override suspend fun getById(id: String): Author =
        // #Validate author id and return the fetched instance
        validateId(id)


}