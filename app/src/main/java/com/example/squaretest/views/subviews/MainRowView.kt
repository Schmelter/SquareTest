package com.example.squaretest.views.subviews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.SubcomposeAsyncImage
import com.example.squaretest.datamodel.EmployeeType
import com.example.squaretest.datamodel.SquareDataElement


@Composable
fun MainRowView(
    dataElement: SquareDataElement,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .border(
                width = 2.dp,
                color = Color.Black
            )
    ) {
        val (uuidText, fullNameText, phoneNumberText, emailAddressText, biographyText,
            photoImage, teamText, employeeTypeText, boxRef) = createRefs()

        var rightRef = ConstrainedLayoutReference("parent").absoluteRight

        dataElement.photoUrlSmall?.let { url ->
            SubcomposeAsyncImage(
                model = url,
                contentDescription = dataElement.fullName,
                loading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .width(5.dp)
                            .height(5.dp)
                    )
                },
                modifier = Modifier
                    .constrainAs(photoImage) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        absoluteRight.linkTo(parent.absoluteRight)

                        height = Dimension.value(100.dp)
                        width = Dimension.value(100.dp)

                        rightRef = photoImage.absoluteLeft
                    }
            )
        }

        ConstraintLayout(
            modifier = Modifier
                .constrainAs(boxRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    absoluteLeft.linkTo(parent.absoluteLeft)
                    absoluteRight.linkTo(rightRef)

                    height = Dimension.wrapContent
                    width = Dimension.fillToConstraints
                }
        ) {
            var topRef = boxRef.top

            Text(
                text = "Name: ${dataElement.fullName}",
                textAlign = TextAlign.Left,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(fullNameText) {
                        top.linkTo(topRef, margin = 5.dp)
                        absoluteLeft.linkTo(parent.absoluteLeft, margin = 5.dp)

                        height = Dimension.wrapContent
                        width = Dimension.percent(0.5f)

                        topRef = fullNameText.bottom
                    }
            )

            Text(
                text = "UUID: ${dataElement.uuid}",
                textAlign = TextAlign.Right,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(uuidText) {
                        top.linkTo(fullNameText.top, margin = 0.dp)
                        absoluteRight.linkTo(parent.absoluteRight, margin = 5.dp)

                        height = Dimension.wrapContent
                        width = Dimension.percent(0.5f)
                    }
            )

            dataElement.phoneNumber?.let {
                Text(
                    text = "Phone: ${dataElement.phoneNumber}",
                    textAlign = TextAlign.Left,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300,
                    modifier = Modifier
                        .constrainAs(phoneNumberText) {
                            top.linkTo(topRef, margin = 5.dp)
                            absoluteLeft.linkTo(parent.absoluteLeft, margin = 5.dp)
                            absoluteRight.linkTo(parent.absoluteRight, margin = 5.dp)

                            height = Dimension.wrapContent
                            width = Dimension.fillToConstraints

                            topRef = phoneNumberText.bottom
                        }
                )
            }

            Text(
                text = "Email: ${dataElement.emailAddress}",
                textAlign = TextAlign.Left,
                fontSize = 12.sp,
                fontWeight = FontWeight.W300,
                modifier = Modifier
                    .constrainAs(emailAddressText) {
                        top.linkTo(topRef, margin = 5.dp)
                        absoluteLeft.linkTo(parent.absoluteLeft, margin = 5.dp)
                        absoluteRight.linkTo(parent.absoluteRight, margin = 5.dp)

                        height = Dimension.wrapContent
                        width = Dimension.fillToConstraints

                        topRef = emailAddressText.bottom
                    }
            )

            dataElement.biography?.let {
                Text(
                    text = "Biography: ${dataElement.biography}",
                    textAlign = TextAlign.Left,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300,
                    modifier = Modifier
                        .constrainAs(biographyText) {
                            top.linkTo(topRef, margin = 5.dp)
                            absoluteLeft.linkTo(parent.absoluteLeft, margin = 5.dp)
                            absoluteRight.linkTo(parent.absoluteRight, margin = 5.dp)

                            height = Dimension.wrapContent
                            width = Dimension.fillToConstraints

                            topRef = biographyText.bottom
                        }
                )
            }

            Text(
                text = "Team: ${dataElement.team}",
                textAlign = TextAlign.Left,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(teamText) {
                        top.linkTo(topRef, margin = 5.dp)
                        absoluteLeft.linkTo(parent.absoluteLeft, margin = 5.dp)
                        bottom.linkTo(parent.bottom, margin = 5.dp)

                        height = Dimension.wrapContent
                        width = Dimension.percent(0.35f)

                        topRef = teamText.bottom
                    }
            )

            Text(
                text = "Employee Type: ${dataElement.employeeType.displayText}",
                textAlign = TextAlign.Right,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(employeeTypeText) {
                        top.linkTo(teamText.top, margin = 0.dp)
                        absoluteRight.linkTo(parent.absoluteRight, margin = 5.dp)
                        bottom.linkTo(parent.bottom, margin = 5.dp)

                        height = Dimension.wrapContent
                        width = Dimension.percent(0.65f)
                    }
            )
        }
    }
}


@Preview(widthDp = 450, heightDp = 200)
@Composable
fun RowWithAllData() {
    val dataElement = SquareDataElement(
        uuid = "UUID 1",
        fullName = "Full Name 1",
        phoneNumber = "Phone Number 1",
        emailAddress = "Email Address 1",
        biography = "Biography 1",
        photoUrlSmall = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
        photoUrlLarge = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
        team = "Team 1",
        employeeType = EmployeeType.FULL_TIME
    )

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Green)
    ) {
        MainRowView(
            dataElement = dataElement,
            modifier = Modifier
                .background(Color.Red)
                .constrainAs(createRef()) {
                    centerHorizontallyTo(parent)

                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
        )
    }
}

@Preview(widthDp = 450, heightDp = 200)
@Composable
fun RowWithNoOptionalData() {
    val dataElement = SquareDataElement(
        uuid = "UUID 1",
        fullName = "Full Name 1",
        phoneNumber = null,
        emailAddress = "Email Address 1",
        biography = null,
        photoUrlSmall = null,
        photoUrlLarge = null,
        team = "Team 1",
        employeeType = EmployeeType.FULL_TIME
    )

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Green)
    ) {
        MainRowView(
            dataElement = dataElement,
            modifier = Modifier
                .background(Color.Red)
                .constrainAs(createRef()) {
                    centerHorizontallyTo(parent)

                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
        )
    }
}