package com.app.mypatnametro.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun RateUsScreen(
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedRating by remember { mutableIntStateOf(0) }
    var feedbackText by remember { mutableStateOf("") }
    var showThankYou by remember { mutableStateOf(false) }
    var hasSubmitted by remember { mutableStateOf(false) }
    var selectedFeedbackOptions by remember { mutableStateOf(setOf<String>()) }
    
    val context = LocalContext.current
    
    if (showThankYou) {
        ThankYouView(onDismiss = onDismiss)
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            RatingView(
                selectedRating = selectedRating,
                onRatingChange = { selectedRating = it },
                feedbackText = feedbackText,
                onFeedbackChange = { feedbackText = it },
                selectedFeedbackOptions = selectedFeedbackOptions,
                onFeedbackOptionToggle = { option ->
                    selectedFeedbackOptions = if (selectedFeedbackOptions.contains(option)) {
                        selectedFeedbackOptions - option
                    } else {
                        selectedFeedbackOptions + option
                    }
                },
                hasSubmitted = hasSubmitted,
                onSubmit = {
                    hasSubmitted = true
                    showThankYou = true
                },
                onOpenPlayStore = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.app.mypatnametro"))
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
private fun RatingView(
    selectedRating: Int,
    onRatingChange: (Int) -> Unit,
    feedbackText: String,
    onFeedbackChange: (String) -> Unit,
    selectedFeedbackOptions: Set<String>,
    onFeedbackOptionToggle: (String) -> Unit,
    hasSubmitted: Boolean,
    onSubmit: () -> Unit,
    onOpenPlayStore: () -> Unit
) {
    // Header Section
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text(
            text = "Rate Our App",
            style = MaterialTheme.typography.headlineMedium.copy(
                textDecoration = TextDecoration.Underline
            ),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )


        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Train,
                contentDescription = "Metro Icon",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        
        Text(
            text = "How was your experience?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "Your feedback helps us improve the Patna Metro app and serve you better",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
        )
    }
    
    // Star Rating Section
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Rate your experience",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(5) { index ->
                val starIndex = index + 1
                val isSelected = starIndex <= selectedRating
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.1f else 1.0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    label = "star_scale"
                )
                
                Icon(
                    if (isSelected) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = "Star $starIndex",
                    modifier = Modifier
                        .size(40.dp)
                        .scale(scale)
                        .padding(horizontal = 4.dp)
                        .clickable { onRatingChange(starIndex) },
                    tint = if (isSelected) Color(0xFFFFD700) else MaterialTheme.colorScheme.outline
                )
            }
        }
        
        if (selectedRating > 0) {
            Text(
                text = getRatingText(selectedRating),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
    
    // Feedback Section
    if (selectedRating > 0) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.ChatBubble,
                    contentDescription = "Feedback",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Tell us more (Optional)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            OutlinedTextField(
                value = feedbackText,
                onValueChange = onFeedbackChange,
                placeholder = { Text("Share your thoughts, suggestions, or report any issues...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                maxLines = 4,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
    
    // Quick Feedback Options for low ratings
    if (selectedRating in 1..3) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Improvements",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "What can we improve?",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(160.dp)
            ) {
                items(quickFeedbackOptions) { option ->
                    QuickFeedbackButton(
                        text = option.text,
                        icon = option.icon,
                        isSelected = selectedFeedbackOptions.contains(option.text),
                        onToggle = { onFeedbackOptionToggle(option.text) }
                    )
                }
            }
        }
    }
    
    // Action Buttons
    if (selectedRating > 0) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onSubmit,
                enabled = !hasSubmitted,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (hasSubmitted) "Submitting..." else "Submit Rating",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            if (selectedRating >= 4) {
                OutlinedButton(
                    onClick = onOpenPlayStore,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Star, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Rate on Play Store",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ThankYouView(onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val scale by animateFloatAsState(
            targetValue = 1.0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            ),
            label = "thank_you_scale"
        )
        
        // Icon
        Box(
            modifier = Modifier
                .size(120.dp)
                .scale(scale)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = "Success",
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Thank You Text
        Text(
            text = "Thank You!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Success Message
        Text(
            text = "Your feedback has been submitted successfully",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Description
        Text(
            text = "We appreciate your time and will use your feedback to make the Patna Metro app even better.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
        )
        
        Spacer(modifier = Modifier.height(30.dp))
        
        // Button
        Button(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Home, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Back to App",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun QuickFeedbackButton(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    Button(
        onClick = onToggle,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (!isSelected) Modifier.border(
                    1.dp,
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    RoundedCornerShape(20.dp)
                ) else Modifier
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

private fun getRatingText(rating: Int): String {
    return when (rating) {
        1 -> "Poor - We're sorry to hear that"
        2 -> "Fair - We can do better"
        3 -> "Good - Thanks for the feedback"
        4 -> "Very Good - We're glad you like it!"
        5 -> "Excellent - You're awesome!"
        else -> ""
    }
}

private data class FeedbackOption(
    val text: String,
    val icon: ImageVector
)

private val quickFeedbackOptions = listOf(
    FeedbackOption("App Speed", Icons.Default.Speed),
    FeedbackOption("User Interface", Icons.Default.Palette),
    FeedbackOption("Real-time Data", Icons.Default.Schedule),
    FeedbackOption("Navigation", Icons.Default.Map),
    FeedbackOption("Notifications", Icons.Default.Notifications),
    FeedbackOption("Other", Icons.Default.MoreHoriz)
)
