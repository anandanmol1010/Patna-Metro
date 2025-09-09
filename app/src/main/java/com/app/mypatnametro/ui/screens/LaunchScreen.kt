package com.app.mypatnametro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.airbnb.lottie.compose.*


@Composable
fun LaunchScreen(
    onLaunchComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Auto-navigate after 3 seconds
    LaunchedEffect(Unit) {
        delay(3000)
        onLaunchComplete()
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1097F0)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            
            // Lottie animation
            val composition by rememberLottieComposition(
                LottieCompositionSpec.Asset("launchscreenanimation.json")
            )
            
            LottieAnimation(
                composition = composition,
                iterations = 2,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.weight(1f))
        }
        
        // Bottom text - positioned absolutely at bottom
        Text(
            text = "Made with ❤️ by Anmol",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}
