package com.example.squaretest.services

import com.example.squaretest.datamodel.ResultWrapper
import com.example.squaretest.datamodel.SquareDataElement

interface SquareDataRepository {

    suspend fun getSquareData(): ResultWrapper<List<SquareDataElement>>

}