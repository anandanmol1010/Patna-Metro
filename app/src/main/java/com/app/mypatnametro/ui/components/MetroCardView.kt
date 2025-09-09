package com.app.mypatnametro.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.mypatnametro.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun MetroCardView(modifier: Modifier = Modifier) {
    var showToast by remember { mutableStateOf(false) }
    val clipboardManager = LocalClipboardManager.current
    
    // Auto-hide toast after delay
    LaunchedEffect(showToast) {
        if (showToast) {
            delay(1200)
            showToast = false
        }
    }
    val cardNumber = "4269 4794 2022"
    val issuedDate = "11/2022"
    
    // Theme gradient
    val themeGradient = Brush.linearGradient(
        colors = listOf(
            GradientStart,    // Red
            GradientOrange,   // Orange
            GradientYellow,   // Yellow
            Color(0xFF1eacfa)      // Blue
        )
    )
    
    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .width(340.dp)
                .height(210.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(themeGradient)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top section with ONE branding
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        // Rotated ONE text
                        Text(
                            text = "ONE",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 2.sp,
                            modifier = Modifier
                                .graphicsLayer(rotationZ = -90f)
                                .padding(top = 24.dp)
                        )
                        
                        // Right side branding
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "ONE DELHI. ONE RIDE.",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                textAlign = TextAlign.End
                            )
                            Text(
                                text = "वन | एक दिल्ली, एक सवारी",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.85f),
                                textAlign = TextAlign.End
                            )
                            Text(
                                text = "DELHI METRO",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White.copy(alpha = 0.9f),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                    
                    // Bottom section with card details
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        // Card number section
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "CARD NO.",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = cardNumber,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                
                                // Copy button
                                IconButton(
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(cardNumber))
                                        showToast = true
                                        // Auto-hide toast after delay
                                        // Use LaunchedEffect for coroutine in Composable context
                                    },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(
                                            Color.White.copy(alpha = 0.2f),
                                            CircleShape
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy card number",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                        
                        // Issued date section
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "ISSUED",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                            Text(
                                text = issuedDate,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
        
        // Toast notification
        if (showToast) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black.copy(alpha = 0.75f)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Copied to clipboard!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                    )
                }
            }
        }
    }
}
