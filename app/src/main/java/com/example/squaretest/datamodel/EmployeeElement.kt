package com.example.squaretest.datamodel

data class EmployeeElement(
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

enum class EmployeeType(val displayText: String) {
    FULL_TIME("Full Time"),
    PART_TIME("Part Time"),
    CONTRACTOR("Contractor")
}