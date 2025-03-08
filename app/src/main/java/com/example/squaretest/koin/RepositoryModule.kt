package com.example.squaretest.koin

import com.example.squaretest.services.SquareDataRepository
import com.example.squaretest.services.SquareDataRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {

    single<SquareDataRepository> { SquareDataRepositoryImpl(get(), Dispatchers.IO) }
}