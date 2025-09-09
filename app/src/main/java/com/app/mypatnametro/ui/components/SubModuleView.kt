package com.app.mypatnametro.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.content.Intent as AndroidIntent
import com.app.mypatnametro.R
import com.app.mypatnametro.data.model.MenuItem

/**
 * Sub module view component for quick actions
 */
@Composable
fun SubModuleView(
    item: MenuItem,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Icon container
        Card(
            modifier = Modifier
                .size(80.dp)
                .clickable {
                    // Handle navigation based on moduleID
                    when (item.moduleID) {
                        4 -> {
                            // Share functionality
                            val shareIntent = AndroidIntent().apply {
                                action = AndroidIntent.ACTION_SEND
                                type = "text/plain"
                                putExtra(AndroidIntent.EXTRA_SUBJECT, "Check this out!")
                                putExtra(
                                    AndroidIntent.EXTRA_TEXT,
                                    "Visit my portfolio site.\nhttps://anandanmol.xyz"
                                )
                            }
                            context.startActivity(AndroidIntent.createChooser(shareIntent, "Share via"))
                        }
                        5 -> navController.navigate("rate_us")
                        6 -> navController.navigate("about_us")
                        7 -> navController.navigate("disclaimer")
                        8 -> navController.navigate("information")
                        9 -> navController.navigate("contact_us")
                        else -> {
                            // Navigate to coming soon for other items
                            navController.navigate("coming_soon")
                        }
                    }
                },
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,

            ) {
                // Icon with drawable resource
                Image(
                    painter = painterResource(
                        id = when (item.imageUrl) {
                            "share" -> R.drawable.share
                            "rate_us" -> R.drawable.rating
                            "about_us" -> R.drawable.about_us
                            "disclaimer" -> R.drawable.disclaimer
                            "information" -> R.drawable.information
                            "contact_us" -> R.drawable.contact_us
                            else -> R.drawable.ic_information
                        }
                    ),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    modifier = Modifier.size(height = 50.dp, width = 50.dp),
                )
            }
        }
    }
}
