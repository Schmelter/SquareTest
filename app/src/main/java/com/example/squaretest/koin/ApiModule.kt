package com.example.squaretest.koin

import com.example.squaretest.api.EmployeeApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single<EmployeeApi> { provideSquareApi(get()) }
}

fun provideSquareApi(retrofit: Retrofit): EmployeeApi =
    retrofit.newBuilder().baseUrl("https://s3.amazonaws.com").build()
        .create(EmployeeApi::class.java)
