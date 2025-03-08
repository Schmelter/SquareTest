package com.example.squaretest.koin

import com.example.squaretest.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        MainViewModel(androidApplication(), get())
    }
}