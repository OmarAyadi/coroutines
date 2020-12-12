package com.moa.coroutines.models.base

import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

/**
 * @author omar Dec 11, 2020
 */
@Document("books")
class Book(
    var id: String? = null,
    val name: String,
    val nbPages: Int,
    val createdAt: Instant = Instant.now()
) {
    @DBRef
    lateinit var author: Author
}