package com.example.squaretest.viewmodels

import android.app.Application
import com.example.squaretest.datamodel.EmployeeElement
import com.example.squaretest.datamodel.EmployeeType
import com.example.squaretest.datamodel.ResultWrapper
import com.example.squaretest.fragments.MainViewModelState
import com.example.squaretest.services.EmployeeRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test

/**
 * Local unit test for the MainViewModel
 */
class MainViewModelTest {

    @Test
    fun `Test First Init ViewModel Expecting Default UIState`() = runTest {
        @MockK
        val application: Application = mockk()

        @MockK
        val employeeRepository: EmployeeRepository = mockk()

        // This should never be called, so throw an exception
        coEvery {
            employeeRepository.getEmployees()
        } throws Exception()

        val mainViewModel = MainViewModel(application, employeeRepository)

        assert(mainViewModel.uiState.value.equals(
            MainViewModelState()
        )) { "UIState not correct" }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test Load Function Expecting isLoading Then Data`() = runTest {
        @MockK
        val application: Application = mockk()

        @MockK
        val employeeRepository: EmployeeRepository = mockk()

        // This should never be called, so throw an exception
        val employees = listOf(
            EmployeeElement(
                uuid = "UUID 1",
                fullName = "Full Name 1",
                phoneNumber = "Phone Number 1",
                emailAddress = "Email Address 1",
                biography = "Biography 1",
                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                team = "Team 1",
                employeeType = EmployeeType.FULL_TIME
            ),
            EmployeeElement(
                uuid = "UUID 2",
                fullName = "Full Name 2",
                phoneNumber = "Phone Number 2",
                emailAddress = "Email Address 2",
                biography = "Biography 2",
                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                team = "Team 2",
                employeeType = EmployeeType.PART_TIME
            ),
            EmployeeElement(
                uuid = "UUID 3",
                fullName = "Full Name 3",
                phoneNumber = "Phone Number 3",
                emailAddress = "Email Address 3",
                biography = "Biography 3",
                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                team = "Team 3",
                employeeType = EmployeeType.CONTRACTOR
            )
        )

        val employeesResult: ResultWrapper<List<EmployeeElement>> = ResultWrapper.Success(
            employees
        )
        coEvery {
            employeeRepository.getEmployees()
        } returns employeesResult

        val mainViewModel = MainViewModel(application, employeeRepository)
        val testResults = mutableListOf<MainViewModelState>()

        val mainDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(mainDispatcher)
        backgroundScope.launch(mainDispatcher) {
            mainViewModel.uiState.toList(testResults)
        }
        mainViewModel.onLoad()

        assert(testResults.size == 3) { "Wrong number of UIState Changes" }
        assert(testResults[0].equals(
            MainViewModelState(
                employees = null,
                errorMessage = null,
                isLoading = false
            )
        )) { "UIState not properly initialized" }
        assert(testResults[1].equals(
            MainViewModelState(
                employees = null,
                errorMessage = null,
                isLoading = true
            )
        )) { "UIState not properly set for loading" }
        assert(testResults[2].equals(
            MainViewModelState(
                employees = employees,
                errorMessage = null,
                isLoading = false
            )
        )) { "UIState not properly set when loaded" }

        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test Reset Function Without Load Expecting No Data`() = runTest {
        @MockK
        val application: Application = mockk()

        @MockK
        val employeeRepository: EmployeeRepository = mockk()

        // This should never be called, so throw an exception
        coEvery {
            employeeRepository.getEmployees()
        } throws Exception()

        val mainViewModel = MainViewModel(application, employeeRepository)
        val testResults = mutableListOf<MainViewModelState>()

        val mainDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(mainDispatcher)
        backgroundScope.launch(mainDispatcher) {
            mainViewModel.uiState.toList(testResults)
        }
        mainViewModel.onReset()

        assert(testResults.size == 1) { "Wrong number of UIState Changes" }
        assert(testResults[0].equals(
            MainViewModelState(
                employees = null,
                errorMessage = null,
                isLoading = false
            )
        )) { "UIState not properly set when reset" }

        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test Load Then Reset Expecting isLoading Then Data Then No Data`() = runTest {
        @MockK
        val application: Application = mockk()

        @MockK
        val employeeRepository: EmployeeRepository = mockk()

        // This should never be called, so throw an exception
        val employees = listOf(
            EmployeeElement(
                uuid = "UUID 1",
                fullName = "Full Name 1",
                phoneNumber = "Phone Number 1",
                emailAddress = "Email Address 1",
                biography = "Biography 1",
                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                team = "Team 1",
                employeeType = EmployeeType.FULL_TIME
            ),
            EmployeeElement(
                uuid = "UUID 2",
                fullName = "Full Name 2",
                phoneNumber = "Phone Number 2",
                emailAddress = "Email Address 2",
                biography = "Biography 2",
                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                team = "Team 2",
                employeeType = EmployeeType.PART_TIME
            ),
            EmployeeElement(
                uuid = "UUID 3",
                fullName = "Full Name 3",
                phoneNumber = "Phone Number 3",
                emailAddress = "Email Address 3",
                biography = "Biography 3",
                photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
                team = "Team 3",
                employeeType = EmployeeType.CONTRACTOR
            )
        )

        val employeesResult: ResultWrapper<List<EmployeeElement>> = ResultWrapper.Success(
            employees
        )
        coEvery {
            employeeRepository.getEmployees()
        } returns employeesResult

        val mainViewModel = MainViewModel(application, employeeRepository)
        val testResults = mutableListOf<MainViewModelState>()

        val mainDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(mainDispatcher)
        backgroundScope.launch(mainDispatcher) {
            mainViewModel.uiState.toList(testResults)
        }
        mainViewModel.onLoad()
        mainViewModel.onReset()

        assert(testResults.size == 4) { "Wrong number of UIState Changes" }
        assert(testResults[0].equals(
            MainViewModelState(
                employees = null,
                errorMessage = null,
                isLoading = false
            )
        )) { "UIState not properly initialized" }
        assert(testResults[1].equals(
            MainViewModelState(
                employees = null,
                errorMessage = null,
                isLoading = true
            )
        )) { "UIState not properly set for loading" }
        assert(testResults[2].equals(
            MainViewModelState(
                employees = employees,
                errorMessage = null,
                isLoading = false
            )
        )) { "UIState not properly set when loaded" }
        assert(testResults[3].equals(
            MainViewModelState(
                employees = null,
                errorMessage = null,
                isLoading = false
            )
        )) { "UIState not properly set when reset" }

        Dispatchers.resetMain()
    }
}