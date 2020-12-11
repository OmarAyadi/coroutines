package com.moa.coroutines.interfaces

import com.moa.coroutines.dto.AuthorDto
import com.moa.coroutines.models.base.Author

/**
 * @author omar Dec 11, 2020
 */
interface IAuthorService : BaseInterface<Author> {
    /** Base Methods **/
    // #Summary method used to create a policy
    // -> This method return ServerError if :
    // * the name already exists in the database
    suspend fun createAuthor(authorDto: AuthorDto): Author

    suspend fun updateAuthor(authorId: String, authorDto: AuthorDto): Author
}