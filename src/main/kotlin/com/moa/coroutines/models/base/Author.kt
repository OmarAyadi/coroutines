package com.moa.coroutines.models.base

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

/**
 * @author omar Dec 11, 2020
 */

@Document("authors")
class Author(
    val firstName: String,
    val lastName: String,
    val email: String,
    val createdAt: Instant = Instant.now()
) {

    @Id
    lateinit var id: String
}