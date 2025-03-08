package com.example.squaretest.services

import com.example.squaretest.api.SquareApi
import com.example.squaretest.datamodel.EmployeeType
import com.example.squaretest.datamodel.ResultWrapper
import com.example.squaretest.datamodel.SquareDataElement
import com.example.squaretest.datamodel.SquareDataElementRaw
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class SquareDataRepositoryImpl(
    private val squareApi: SquareApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): SquareDataRepository {

    override suspend fun getSquareData(): ResultWrapper<List<SquareDataElement>> {
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

    fun parseRawResult(rawResults: List<SquareDataElementRaw>): List<SquareDataElement> {
        val mutableResults = mutableListOf<SquareDataElement>()
        for (rawResult in rawResults) {
            // Check that everything exists that should, and parse the enum
            val uuid: String = rawResult.uuid ?: throw ParseException("Cannot parse UUID: MISSING")
            val fullName: String = rawResult.fullName ?: throw ParseException("Cannot parse Full Name: MISSING")
            val emailAddress: String = rawResult.emailAddress ?: throw ParseException("Cannot parse Email Address: MISSING")
            val team: String = rawResult.team ?: throw ParseException("Cannot parse Team: MISSING")
            val employeeType: EmployeeType = if (rawResult.employeeType == null) {
                throw ParseException("Cannot parse Employee Type: MISSING")
            } else {
                try {
                    EmployeeType.valueOf(rawResult.employeeType)
                } catch (ex: IllegalArgumentException) {
                    throw ParseException("Cannot parse Employee Type: ${rawResult.employeeType}")
                }
            }

            mutableResults.add(
                SquareDataElement(
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