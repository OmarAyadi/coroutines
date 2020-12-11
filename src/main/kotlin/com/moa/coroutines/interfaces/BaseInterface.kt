package com.moa.coroutines.interfaces

import kotlinx.coroutines.flow.Flow
import org.springframework.web.server.ResponseStatusException

/**
 * @author omar Dec 11, 2020
 */
interface BaseInterface<T> {
    /** Authorization Methods **/
    // #Summary #Validaion method used to check if the id exists in the database
    @Throws(ResponseStatusException::class)
    suspend fun validateId(id: String): T

    /** Base Methods **/
    // #Summary method used to delete any T instance
    suspend fun deleteById(id: String): String

    // #Summary method used to return all T instances
    suspend fun getAll(): Flow<T>

    // #Summary method used to return a T instance by id
    suspend fun getById(id: String): T
}