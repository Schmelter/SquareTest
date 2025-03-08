package com.example.squaretest.datamodel

import com.google.gson.annotations.SerializedName

data class SquareDataElement(
    val uuid: String,
    val fullName: String,
    val phoneNumber: String?,
    val emailAddress: String,
    val biography: String?,
    val photoUrlSmall: String?,
    val photoUrlLarge: String?,
    val team: String,
    val employeeType: EmployeeType
)

enum class EmployeeType(type: String) {
    FULL_TIME("FULL_TIME"),
    PART_TIME("PART_TIME"),
    CONTRACTOR("CONTRACTOR")
}