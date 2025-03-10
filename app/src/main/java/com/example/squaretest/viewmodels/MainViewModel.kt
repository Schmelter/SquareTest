package com.example.squaretest.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.squaretest.datamodel.ResultWrapper
import com.example.squaretest.fragments.MainViewModelState
import com.example.squaretest.services.EmployeeRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val application: Application,
    private val squareResp: EmployeeRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainViewModelState> = MutableStateFlow(MainViewModelState())
    val uiState: StateFlow<MainViewModelState> = _uiState.asStateFlow()

    private val _exceptions = Channel<Throwable>(Channel.BUFFERED)
    val exceptions = _exceptions.receiveAsFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _exceptions.send(throwable)
        }
    }

    private fun updateUiState(updateAction: (MainViewModelState) -> MainViewModelState) {
        _uiState.update {
            updateAction.invoke(it)
        }
    }

    fun onLoad() {
        updateUiState { old ->
            old.copy(
                employees = null,
                errorMessage = null,
                isLoading = true
            )
        }

        viewModelScope.launch(coroutineExceptionHandler) {
            val result = squareResp.getEmployees()
            when (result) {
                is ResultWrapper.Success -> {
                    updateUiState { old ->
                        old.copy(
                            employees = result.data?.sortedBy { employee ->
                                employee.fullName
                            },
                            errorMessage = null,
                            isLoading = false
                        )
                    }
                }
                is ResultWrapper.ParsingError -> {
                    updateUiState { old ->
                        old.copy(
                            employees = null,
                            errorMessage = result.exception?.message ?: "Error without Message",
                            isLoading = false
                        )
                    }
                }
                is ResultWrapper.HttpStatusError -> {
                    updateUiState { old ->
                        old.copy(
                            employees = null,
                            errorMessage = result.exception?.message ?: "Error without Message",
                            isLoading = false
                        )
                    }
                }
                is ResultWrapper.GenericError -> {
                    updateUiState { old ->
                        old.copy(
                            employees = null,
                            errorMessage = result.exception?.message ?: "Error without Message",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onReset() {
        updateUiState { old ->
            old.copy(
                employees = null,
                errorMessage = null,
                isLoading = false
            )
        }
    }
}