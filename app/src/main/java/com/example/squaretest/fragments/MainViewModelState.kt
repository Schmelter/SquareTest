package com.example.squaretest.fragments

import com.example.squaretest.datamodel.EmployeeElement

data class MainViewModelState(
    val employees: List<EmployeeElement>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)