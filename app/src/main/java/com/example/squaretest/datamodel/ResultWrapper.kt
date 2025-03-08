package com.example.squaretest.datamodel

sealed class ResultWrapper<out T : Any> {
    class Success<out T : Any>(val data: T?) : ResultWrapper<T>()
    data class HttpStatusError(val exception: Exception? = null, val httpStatusCode: Int = 0) : ResultWrapper<Nothing>()
    data class ParsingError(val message: Exception? = null) : ResultWrapper<Nothing>()
    data class GenericError(val exception: Exception? = null) : ResultWrapper<Nothing>()
}