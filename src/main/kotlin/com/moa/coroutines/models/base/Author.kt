package com.moa.coroutines.models.base

import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

/**
 * @author omar Dec 11, 2020
 */

@Document("authors")
class Author(
    var id: String? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val createdAt: Instant = Instant.now()
)