package com.example.squaretest.services

import com.example.squaretest.api.EmployeeApi
import com.example.squaretest.datamodel.EmployeeElement
import com.example.squaretest.datamodel.EmployeeElementRaw
import com.example.squaretest.datamodel.EmployeeElementRawArray
import com.example.squaretest.datamodel.ResultWrapper
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

/**
 * Local unit test for the EmployeeRepository
 */
class EmployeeRepositoryTest {

    @Test
    fun `Test Http 401 Status Expecting HttpStatusError`() = runTest {
        val failureEmployeeApi = object : EmployeeApi {
            override suspend fun getEmployeesData(): Response<EmployeeElementRawArray> {
                return Response.error(401,
                    "Utter Garbage".toResponseBody("application/json".toMediaTypeOrNull())
                )
            }
        }

        val repository = EmployeeRepositoryImpl(failureEmployeeApi)
        val result = repository.getEmployees()

        assert(result is ResultWrapper.HttpStatusError) { "Wrong ResultWrapper Type" }
        val failure = result as ResultWrapper.HttpStatusError
        assert(failure.httpStatusCode == 401) { "Wrong Result Data Type" }
        assert(failure.exception is Exception) { "No Exception Supplied" }
    }

    @Test
    fun `Test Http 200 Status Empty Body Expecting ParsingError`() = runTest {
        val failureEmployeeApi = object : EmployeeApi {
            override suspend fun getEmployeesData(): Response<EmployeeElementRawArray> {
                return Response.success(null)
            }
        }

        val repository = EmployeeRepositoryImpl(failureEmployeeApi)
        val result = repository.getEmployees()

        assert(result is ResultWrapper.ParsingError) { "Wrong ResultWrapper Type" }
        val failure = result as ResultWrapper.ParsingError
        assert(failure.exception is Exception) { "No Exception Supplied" }
    }

    @Test
    fun `Test Http Body Throws Exception Expecting GenericError`() = runTest {
        @MockK
        val exceptionResponse: Response<EmployeeElementRawArray> = mockk()

        coEvery {
           exceptionResponse.body()
        } throws Exception()

        val failureEmployeeApi = object : EmployeeApi {
            override suspend fun getEmployeesData(): Response<EmployeeElementRawArray> {
                return exceptionResponse
            }
        }

        val repository = EmployeeRepositoryImpl(failureEmployeeApi)
        val result = repository.getEmployees()

        assert(result is ResultWrapper.GenericError) { "Wrong ResultWrapper Type" }
        val failure = result as ResultWrapper.GenericError
        assert(failure.exception is Exception) { "No Exception Supplied" }
    }

    @Test
    fun `Test Parsing Empty Employees Expecting Empty List`() = runTest {
        val emptyEmployeeApi = object : EmployeeApi {
            override suspend fun getEmployeesData(): Response<EmployeeElementRawArray> {
                return Response.success(200,
                    EmployeeElementRawArray(listOf())
                )
            }
        }

        val repository = EmployeeRepositoryImpl(emptyEmployeeApi)
        val result = repository.getEmployees()

        assert(result is ResultWrapper.Success) { "Wrong ResultWrapper Type" }
        val success = result as ResultWrapper.Success
        assert(success.data is List<EmployeeElement>) { "Wrong Result Data Type" }
        val data = result.data as List<EmployeeElement>
        assert(data.size == 0) { "Wrong Number of Employees" }
    }

    @Test
    fun `Test Parsing Many Employees Expecting Full List`() = runTest {
        val fullEmployeeApi = object : EmployeeApi {
            override suspend fun getEmployeesData(): Response<EmployeeElementRawArray> {
                return Response.success(200,
                    EmployeeElementRawArray(
                         listOf(
                            EmployeeElementRaw(
                                uuid = "UUID 1",
                                fullName = "Full Name 1",
                                phoneNumber = "Phone Number 1",
                                emailAddress = "Email Address 1",
                                biography = "Biography 1",
                                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                team = "Team 1",
                                employeeType = "FULL_TIME"
                            ),
                             EmployeeElementRaw(
                                uuid = "UUID 2",
                                fullName = "Full Name 2",
                                phoneNumber = "Phone Number 2",
                                emailAddress = "Email Address 2",
                                biography = "Biography 2",
                                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                team = "Team 2",
                                employeeType = "PART_TIME"
                            ),
                             EmployeeElementRaw(
                                uuid = "UUID 3",
                                fullName = "Full Name 3",
                                phoneNumber = "Phone Number 3",
                                emailAddress = "Email Address 3",
                                biography = "Biography 3",
                                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                team = "Team 3",
                                employeeType = "CONTRACTOR"
                            )
                        )
                    )
                )
            }
        }

        val repository = EmployeeRepositoryImpl(fullEmployeeApi)
        val result = repository.getEmployees()

        assert(result is ResultWrapper.Success) { "Wrong ResultWrapper Type" }
        val success = result as ResultWrapper.Success
        assert(success.data is List<EmployeeElement>) { "Wrong Result Data Type" }
        val data = result.data as List<EmployeeElement>
        assert(data.size == 3) { "Wrong Number of Employees" }
    }

    @Test
    fun `Test Parsing Malformed Employees Expecting Filtered List`() = runTest {
        val malformedEmployeeApi = object : EmployeeApi {
            override suspend fun getEmployeesData(): Response<EmployeeElementRawArray> {
                return Response.success(200,
                    EmployeeElementRawArray(
                        listOf(
                            // The first 3 are perfectly fine
                            EmployeeElementRaw(
                                uuid = "UUID 1",
                                fullName = "Full Name 1",
                                phoneNumber = "Phone Number 1",
                                emailAddress = "Email Address 1",
                                biography = "Biography 1",
                                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                team = "Team 1",
                                employeeType = "FULL_TIME"
                            ),
                            EmployeeElementRaw(
                                uuid = "UUID 2",
                                fullName = "Full Name 2",
                                phoneNumber = "Phone Number 2",
                                emailAddress = "Email Address 2",
                                biography = "Biography 2",
                                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                team = "Team 2",
                                employeeType = "PART_TIME"
                            ),
                            EmployeeElementRaw(
                                uuid = "UUID 3",
                                fullName = "Full Name 3",
                                phoneNumber = "Phone Number 3",
                                emailAddress = "Email Address 3",
                                biography = "Biography 3",
                                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                team = "Team 3",
                                employeeType = "CONTRACTOR"
                            ),
                            // These are all malformed in different ways, and should be filtered
                            EmployeeElementRaw(
                                uuid = null,
                                fullName = "Full Name 3",
                                phoneNumber = "Phone Number 3",
                                emailAddress = "Email Address 3",
                                biography = "Biography 3",
                                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                team = "Team 3",
                                employeeType = "CONTRACTOR"
                            ),
                            EmployeeElementRaw(
                                uuid = "UUID 3",
                                fullName = null,
                                phoneNumber = "Phone Number 3",
                                emailAddress = "Email Address 3",
                                biography = "Biography 3",
                                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                team = "Team 3",
                                employeeType = "CONTRACTOR"
                            ),
                            EmployeeElementRaw(
                                uuid = "UUID 3",
                                fullName = "Full Name 3",
                                phoneNumber = "Phone Number 3",
                                emailAddress = null,
                                biography = "Biography 3",
                                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                team = "Team 3",
                                employeeType = "CONTRACTOR"
                            ),
                            EmployeeElementRaw(
                                uuid = "UUID 3",
                                fullName = "Full Name 3",
                                phoneNumber = "Phone Number 3",
                                emailAddress = "Email Address 3",
                                biography = "Biography 3",
                                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                team = null,
                                employeeType = "CONTRACTOR"
                            ),
                            EmployeeElementRaw(
                                uuid = "UUID 3",
                                fullName = "Full Name 3",
                                phoneNumber = "Phone Number 3",
                                emailAddress = "Email Address 3",
                                biography = "Biography 3",
                                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                team = "Team 3",
                                employeeType = null
                            ),
                            EmployeeElementRaw(
                                uuid = "UUID 3",
                                fullName = "Full Name 3",
                                phoneNumber = "Phone Number 3",
                                emailAddress = "Email Address 3",
                                biography = "Biography 3",
                                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                                team = "Team 3",
                                employeeType = "GARBAGE"
                            )
                        )
                    )
                )
            }
        }

        val repository = EmployeeRepositoryImpl(malformedEmployeeApi)
        val result = repository.getEmployees()

        assert(result is ResultWrapper.Success) { "Wrong ResultWrapper Type" }
        val success = result as ResultWrapper.Success
        assert(success.data is List<EmployeeElement>) { "Wrong Result Data Type" }
        val data = result.data as List<EmployeeElement>
        assert(data.size == 3) { "Wrong Number of Employees" }
    }
}