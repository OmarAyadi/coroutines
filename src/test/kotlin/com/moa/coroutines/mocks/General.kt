package com.moa.coroutines.mocks

import org.bson.types.ObjectId

/**
 * @author omar Oct 23, 2020
 */

const val invalid = "invalid"

const val authorId = "valid author id for testing"

val generateId : String
    get() = ObjectId().toString()
