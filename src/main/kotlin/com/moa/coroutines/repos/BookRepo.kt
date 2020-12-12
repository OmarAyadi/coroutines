package com.moa.coroutines.repos

import com.moa.coroutines.models.base.Book
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

/**
 * @author omar Dec 11, 2020
 */
interface BookRepo : CoroutineCrudRepository<Book, String> {
    suspend fun findByName(name: String): Book?

    suspend fun findByAuthorId(authorId: String): Flow<Book>
}