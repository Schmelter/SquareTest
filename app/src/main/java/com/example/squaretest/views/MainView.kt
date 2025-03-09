package com.example.squaretest.views

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Vertical
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import com.example.squaretest.datamodel.EmployeeType
import com.example.squaretest.datamodel.SquareDataElement
import com.example.squaretest.fragments.MainViewModelState
import com.example.squaretest.views.subviews.MainRowView

@Composable
fun MainView(
    uiState: MainViewModelState,
    onLoad: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        if (uiState.isLoading) {
            // When loading, only show the spinner, nothing else
            CircularProgressIndicator(
                modifier = Modifier
                    .constrainAs(createRef()) {
                        centerHorizontallyTo(parent)
                        centerVerticallyTo(parent, 0.3f)

                        width = Dimension.value(64.dp)
                        height = Dimension.value(64.dp)
                    },
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        } else if (uiState.employees != null) {
            if (uiState.employees.isEmpty()) {
                val (resetButton, noEmpText) = createRefs()

                Button(
                    modifier = Modifier
                        .constrainAs(resetButton) {
                            bottom.linkTo(parent.bottom, margin = 10.dp)
                            absoluteRight.linkTo(parent.absoluteRight, margin = 10.dp)
                            absoluteLeft.linkTo(parent.absoluteLeft, margin = 10.dp)

                            height = Dimension.value(50.dp)
                            width = Dimension.fillToConstraints
                        },
                    onClick = onReset
                ) {
                    Text("Reset")
                }

                Text(
                    text = "Empty List of Employees Downloaded",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(noEmpText) {
                            bottom.linkTo(resetButton.top, margin = 10.dp)
                            absoluteRight.linkTo(parent.absoluteRight, margin = 20.dp)
                            absoluteLeft.linkTo(parent.absoluteLeft, margin = 20.dp)

                            height = Dimension.wrapContent
                            width = Dimension.fillToConstraints
                        }
                )
            } else {
                val (resetButton, listColumn) = createRefs()

                LazyColumn(
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    flingBehavior = ScrollableDefaults.flingBehavior(),
                    userScrollEnabled = true,
                    modifier = Modifier
                        .constrainAs(listColumn) {
                            centerHorizontallyTo(parent)
                            top.linkTo(parent.top)
                            bottom.linkTo(resetButton.top, margin = 10.dp)

                            height = Dimension.fillToConstraints
                            width = Dimension.fillToConstraints
                        }
                ) {
                    items(items = uiState.employees) { employee ->
                        MainRowView(
                            dataElement = employee,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .constrainAs(resetButton) {
                            bottom.linkTo(parent.bottom, margin = 10.dp)
                            absoluteRight.linkTo(parent.absoluteRight, margin = 10.dp)
                            absoluteLeft.linkTo(parent.absoluteLeft, margin = 10.dp)

                            height = Dimension.value(50.dp)
                            width = Dimension.fillToConstraints
                        },
                    onClick = onReset
                ) {
                    Text("Reset")
                }
            }
        } else {
            val (loadButton, errorText) = createRefs()

            Button(
                modifier = Modifier
                    .constrainAs(loadButton) {
                        top.linkTo(parent.top, margin = 10.dp)
                        absoluteRight.linkTo(parent.absoluteRight, margin = 10.dp)
                        absoluteLeft.linkTo(parent.absoluteLeft, margin = 10.dp)

                        height = Dimension.value(50.dp)
                        width = Dimension.fillToConstraints
                    },
                onClick = onLoad
            ) {
                Text("Load")
            }

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .constrainAs(errorText) {
                            top.linkTo(loadButton.bottom, margin = 10.dp)
                            absoluteRight.linkTo(parent.absoluteRight, margin = 20.dp)
                            absoluteLeft.linkTo(parent.absoluteLeft, margin = 20.dp)
                            
                            height = Dimension.wrapContent
                            width = Dimension.fillToConstraints
                        }
                )
            }
        }
    }
}

@Preview(widthDp = 450, heightDp = 500)
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
            onReset = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(widthDp = 450, heightDp = 500)
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
            onReset = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(widthDp = 450, heightDp = 500)
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
            onReset = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(widthDp = 450, heightDp = 500)
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
            onReset = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(widthDp = 450, heightDp = 500)
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
            onReset = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(widthDp = 450, heightDp = 500)
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
            onReset = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
