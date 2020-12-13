package com.moa.coroutines.mocks

import org.bson.types.ObjectId

/**
 * @author omar Oct 23, 2020
 */

const val invalidId = "invalid id"

const val invalidName = "invalid name"

val generateId : String
    get() = ObjectId().toString()
