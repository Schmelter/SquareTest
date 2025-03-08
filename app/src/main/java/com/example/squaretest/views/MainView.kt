package com.example.squaretest.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.example.squaretest.datamodel.EmployeeType
import com.example.squaretest.datamodel.SquareDataElement
import com.example.squaretest.fragments.MainViewModelState

@Composable
fun MainView(
    uiState: MainViewModelState,
    onLoad: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {

}

@Preview(widthDp = 327, heightDp = 500)
@Composable
fun MainViewNoDataNoMessage() {
    val uiState = MainViewModelState(
        employees = null,
        errorMessage = null,
        isLoading = false
    )

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Green)
    ) {
        MainView(
            uiState,
            onLoad = {},
            onReset = {}
        )
    }
}

@Preview(widthDp = 327, heightDp = 500)
@Composable
fun MainViewNoDataWithMessage() {
    val uiState = MainViewModelState(
        employees = null,
        errorMessage = "This is an Error Message!",
        isLoading = false
    )

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Green)
    ) {
        MainView(
            uiState,
            onLoad = {},
            onReset = {}
        )
    }
}

@Preview(widthDp = 327, heightDp = 500)
@Composable
fun MainViewLoading() {
    val uiState = MainViewModelState(
        employees = null,
        errorMessage = null,
        isLoading = true
    )

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Green)
    ) {
        MainView(
            uiState,
            onLoad = {},
            onReset = {}
        )
    }
}

@Preview(widthDp = 327, heightDp = 500)
@Composable
fun MainViewWithNoEmployees() {
    val uiState = MainViewModelState(
        employees = listOf(

        ),
        errorMessage = null,
        isLoading = false
    )

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Green)
    ) {
        MainView(
            uiState,
            onLoad = {},
            onReset = {}
        )
    }
}

@Preview(widthDp = 327, heightDp = 500)
@Composable
fun MainViewWithManyEmployeesAllData() {
    val uiState = MainViewModelState(
        employees = listOf(
            SquareDataElement(
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
            SquareDataElement(
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
            SquareDataElement(
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
        ),
        errorMessage = null,
        isLoading = false
    )

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Green)
    ) {
        MainView(
            uiState,
            onLoad = {},
            onReset = {}
        )
    }
}

@Preview(widthDp = 327, heightDp = 500)
@Composable
fun MainViewWithManyEmployeesNoData() {
    val uiState = MainViewModelState(
        employees = listOf(
            SquareDataElement(
                uuid = "UUID 1",
                fullName = "Full Name 1",
                phoneNumber = null,
                emailAddress = "Email Address 1",
                biography = null,
                photoUrlSmall = null,
                photoUrlLarge = null,
                team = "Team 1",
                employeeType = EmployeeType.FULL_TIME
            ),
            SquareDataElement(
                uuid = "UUID 2",
                fullName = "Full Name 2",
                phoneNumber = null,
                emailAddress = "Email Address 2",
                biography = null,
                photoUrlSmall = null,
                photoUrlLarge = null,
                team = "Team 2",
                employeeType = EmployeeType.PART_TIME
            ),
            SquareDataElement(
                uuid = "UUID 3",
                fullName = "Full Name 3",
                phoneNumber = null,
                emailAddress = "Email Address 3",
                biography = null,
                photoUrlSmall = null,
                photoUrlLarge = null,
                team = "Team 3",
                employeeType = EmployeeType.CONTRACTOR
            )
        ),
        errorMessage = null,
        isLoading = false
    )

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Green)
    ) {
        MainView(
            uiState,
            onLoad = {},
            onReset = {}
        )
    }
}
