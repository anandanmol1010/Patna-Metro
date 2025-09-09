package com.app.mypatnametro.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.mypatnametro.R


@Composable
fun ComingSoonView(modifier: Modifier = Modifier) {
    var fadeIn by remember { mutableStateOf(false) }
    var blink by remember { mutableStateOf(true) }
    
    // Animation for fade in effect
    val fadeInAnimation by animateFloatAsState(
        targetValue = if (fadeIn) 1f else 0f,
        animationSpec = tween(durationMillis = 600, easing = EaseOut),
        label = "fadeIn"
    )
    
    // Animation for blinking cursor
    val blinkAnimation by animateFloatAsState(
        targetValue = if (blink) 1f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blink"
    )
    
    LaunchedEffect(Unit) {
        fadeIn = true
    }
    
    LaunchedEffect(Unit) {
        while (true) {
            blink = !blink
            kotlinx.coroutines.delay(600)
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Logo image
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .graphicsLayer(alpha = fadeInAnimation)
                    .offset(y = if (fadeIn) 0.dp else 20.dp),
                contentScale = ContentScale.Crop
            )
            
            // Title with blinking cursor
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Coming Soon",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.displayMedium.copy(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF235DE7).copy(alpha = 0.8f),
                                Color(0xFF235DE7)
                            )
                        )
                    ),
                    modifier = Modifier
                        .graphicsLayer(alpha = fadeInAnimation)
                        .offset(y = if (fadeIn) 0.dp else 20.dp)
                )
                
                // Blinking cursor
                Text(
                    text = "|",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    modifier = Modifier.graphicsLayer(alpha = blinkAnimation)
                )
            }
        }
    }
}
