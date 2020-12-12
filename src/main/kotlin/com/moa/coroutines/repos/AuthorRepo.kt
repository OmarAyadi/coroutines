package com.moa.coroutines.repos

import com.moa.coroutines.models.base.Author
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

/**
 * @author omar Dec 11, 2020
 */
interface AuthorRepo : CoroutineCrudRepository<Author, String>