package com.app.mypatnametro.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min


@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Map image with gesture handling
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures(
                        onGesture = { _, pan, zoom, _ ->
                            // Update scale with limits (0.5x to 4x)
                            scale = (scale * zoom).coerceIn(0.5f, 4f)
                            
                            // Update offset for panning
                            offset = Offset(
                                x = offset.x + pan.x,
                                y = offset.y + pan.y
                            )
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            // Metro map placeholder
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Placeholder for metro map
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "🗺️",
                            style = MaterialTheme.typography.displayLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Patna Metro Map",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Pinch to zoom • Drag to pan",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Metro lines representation
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(
                                            MaterialTheme.colorScheme.error,
                                            androidx.compose.foundation.shape.CircleShape
                                        )
                                )
                                Text(
                                    text = "Red Line (East-West)",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(
                                            MaterialTheme.colorScheme.primary,
                                            androidx.compose.foundation.shape.CircleShape
                                        )
                                )
                                Text(
                                    text = "Blue Line (North-South)",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
        
        // Reset button (floating action button)
        FloatingActionButton(
            onClick = {
                scale = 1f
                offset = Offset.Zero
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Text(
                text = "↻",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        
        // Zoom level indicator
        Card(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
            )
        ) {
            Text(
                text = "Zoom: ${String.format("%.1f", scale)}x",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
