package com.example.squaretest.services

import com.example.squaretest.api.EmployeeApi
import com.example.squaretest.datamodel.EmployeeType
import com.example.squaretest.datamodel.ResultWrapper
import com.example.squaretest.datamodel.EmployeeElement
import com.example.squaretest.datamodel.EmployeeElementRawArray
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class EmployeeRepositoryImpl(
    private val squareApi: EmployeeApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): EmployeeRepository {

    override suspend fun getEmployees(): ResultWrapper<List<EmployeeElement>> {
        return withContext(dispatcher) {
            try {
                val rawResults = squareApi.getEmployeesData()
                if (rawResults.isSuccessful) {
                    rawResults.body()?.let { body ->
                        try {
                            ResultWrapper.Success(
                                parseRawResult(body)
                            )
                        } catch (ex: Exception) {
                            ResultWrapper.ParsingError(ex)
                        }
                    } ?: run {
                        // Empty Response?
                        ResultWrapper.ParsingError(Exception("No Data Returned"))
                    }
                } else {
                    ResultWrapper.HttpStatusError(Exception("Unknown Http Status Failure"), rawResults.code())
                }
            } catch (ex: Exception) {
                ResultWrapper.GenericError(ex)
            }
        }
    }

    private fun parseRawResult(rawResults: EmployeeElementRawArray): List<EmployeeElement> {
        val mutableResults = mutableListOf<EmployeeElement>()
        for (rawResult in rawResults.employees) {
            // Check that everything exists that should, and parse the enum
            val uuid: String = rawResult.uuid ?: continue
            val fullName: String = rawResult.fullName ?: continue
            val emailAddress: String = rawResult.emailAddress ?: continue
            val team: String = rawResult.team ?: continue
            val employeeType: EmployeeType = if (rawResult.employeeType == null) {
                continue
            } else {
                try {
                    EmployeeType.valueOf(rawResult.employeeType)
                } catch (ex: IllegalArgumentException) {
                    continue
                }
            }

            mutableResults.add(
                EmployeeElement(
                    uuid,
                    fullName,
                    rawResult.phoneNumber,
                    emailAddress,
                    rawResult.biography,
                    rawResult.photoUrlSmall,
                    rawResult.photoUrlLarge,
                    team,
                    employeeType
                )
            )
        }
        return mutableResults
    }
}

class ParseException(message: String) : Exception(message) {

}