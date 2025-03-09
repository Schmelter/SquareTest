package com.example.squaretest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.squaretest.ui.theme.SquareTestTheme
import com.example.squaretest.viewmodels.MainViewModel
import com.example.squaretest.views.MainView
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MainFragment: Fragment(0) {

    private val viewModel: MainViewModel by activityViewModel()
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SquareTestTheme {
                    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
                    MainView(
                        uiState = uiState.value,
                        onLoad = viewModel::onLoad,
                        onReset = viewModel::onReset
                    )
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.exceptions.catch { e ->
                    // Exception in the flow itself?
                    Toast.makeText(this@MainFragment.context, e.message, Toast.LENGTH_SHORT)
                }.collect { exception ->
                    Toast.makeText(this@MainFragment.context, exception.message, Toast.LENGTH_SHORT)
                }
            }
        }
    }

}