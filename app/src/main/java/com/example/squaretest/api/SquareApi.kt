package com.example.squaretest.api

import com.example.squaretest.datamodel.SquareDataElementRaw
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface SquareApi {

    @Headers("Accept: application/json", "Content-Type: application/json", "No-Auth-Header: true")
    @GET("/sq-mobile-interview/employees.json")
    suspend fun getEmployeesData(
    ): Response<List<SquareDataElementRaw>>
}