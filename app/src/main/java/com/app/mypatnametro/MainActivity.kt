package com.app.mypatnametro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.mypatnametro.navigation.PatnaMetroNavigation
import com.app.mypatnametro.ui.screens.LaunchScreen
import com.app.mypatnametro.ui.screens.OnboardingScreen
import com.app.mypatnametro.ui.screens.DisclaimerScreen
import com.app.mypatnametro.ui.theme.MypatnametroTheme
import com.app.mypatnametro.viewmodel.AppState
import com.app.mypatnametro.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Initially hide status bar and navigation bar for launch screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        
        setContent {
            MypatnametroTheme {
                PatnaMetroApp()
            }
        }
    }
}

@Composable
fun PatnaMetroApp(
    viewModel: MainViewModel = hiltViewModel()
) {
    val appState by viewModel.appState.collectAsStateWithLifecycle()
    
    val context = LocalContext.current
    
    // Show navigation buttons only when not in launch screen
    LaunchedEffect(appState) {
        val activity = context as ComponentActivity
        val windowInsetsController = WindowCompat.getInsetsController(activity.window, activity.window.decorView)
        
        when (appState) {
            AppState.LAUNCH -> {
                // Hide navigation buttons during launch screen
                windowInsetsController.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
            }
            else -> {
                // Show navigation buttons for all other screens
                windowInsetsController.show(WindowInsetsCompat.Type.navigationBars())
                windowInsetsController.hide(WindowInsetsCompat.Type.statusBars()) // Keep status bar hidden
            }
        }
    }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (appState) {
            AppState.LAUNCH -> {
                LaunchScreen(
                    onLaunchComplete = {
                        viewModel.onLaunchComplete()
                    }
                )
            }
            AppState.ONBOARDING -> {
                OnboardingScreen(
                    onOnboardingComplete = {
                        viewModel.onOnboardingComplete()
                    }
                )
            }
            AppState.DISCLAIMER -> {
                DisclaimerScreen(
                    onDismiss = {
                        viewModel.onDisclaimerAccepted()
                    },
                    isFirstTime = !viewModel.isDisclaimerAccepted()
                )
            }
            AppState.MAIN -> {
                PatnaMetroNavigation()
            }
        }
    }
}