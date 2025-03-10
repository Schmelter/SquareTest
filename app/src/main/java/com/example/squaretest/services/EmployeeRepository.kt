package com.example.squaretest.services

import com.example.squaretest.datamodel.ResultWrapper
import com.example.squaretest.datamodel.EmployeeElement

interface EmployeeRepository {

    suspend fun getEmployees(): ResultWrapper<List<EmployeeElement>>

}