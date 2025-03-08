package com.example.squaretest.fragments

import com.example.squaretest.datamodel.SquareDataElement

data class MainViewModelState(
    val employees: List<SquareDataElement>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)