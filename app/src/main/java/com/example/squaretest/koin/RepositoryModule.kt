package com.example.squaretest.koin

import com.example.squaretest.services.EmployeeRepository
import com.example.squaretest.services.EmployeeRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {

    single<EmployeeRepository> { EmployeeRepositoryImpl(get(), Dispatchers.IO) }
}