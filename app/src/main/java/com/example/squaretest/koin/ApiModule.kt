package com.example.squaretest.koin

import com.example.squaretest.api.SquareApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single<SquareApi> { provideSquareApi(get()) }
}

fun provideSquareApi(retrofit: Retrofit): SquareApi =
    retrofit.newBuilder().baseUrl("https://s3.amazonaws.com").build()
        .create(SquareApi::class.java)
