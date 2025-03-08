package com.example.squaretest

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.squaretest.ui.theme.SquareTestTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepSplashScreen = true
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            delay(3000)
            keepSplashScreen = false
        }

        setContent {
            SquareTestTheme {
                AndroidView(
                    modifier = Modifier
                        .fillMaxSize(),
                    factory = { ctx ->
                        FragmentContainerView(ctx).apply {
                            id = R.id.nav_host_fragment
                            layoutParams = FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT
                            )
                        }
                    },
                    update = { fragmentContainerView ->
                        if (supportFragmentManager.findFragmentById(fragmentContainerView.id) == null) {
                            val navHostFragment = NavHostFragment.create(R.navigation.mobile_navigation)
                            supportFragmentManager.beginTransaction()
                                .replace(fragmentContainerView.id, navHostFragment)
                                .setPrimaryNavigationFragment(navHostFragment)
                                .commitNow()

                            this.navController = navHostFragment.navController
                        }
                    }
                )
            }
        }
    }
}