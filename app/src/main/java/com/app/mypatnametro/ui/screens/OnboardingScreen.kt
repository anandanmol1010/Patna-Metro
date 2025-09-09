package com.app.mypatnametro.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val onboardingPages = listOf(
        OnboardingPage(
            title = "Welcome to Patna Metro",
            description = "Your smart companion for navigating Patna's metro system with ease and convenience.",
            emoji = "🚇"
        ),
        OnboardingPage(
            title = "Find Your Route",
            description = "Discover the fastest routes between stations with our intelligent path-finding system.",
            emoji = "🗺️"
        ),
        OnboardingPage(
            title = "Interactive Metro Map",
            description = "Explore the complete metro network with zoom and pan gestures for detailed navigation.",
            emoji = "📍"
        ),
        OnboardingPage(
            title = "Digital Metro Card",
            description = "Access your metro card digitally and manage your travel expenses efficiently.",
            emoji = "💳"
        )
    )
    
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val coroutineScope = rememberCoroutineScope()
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Skip button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onOnboardingComplete
                ) {
                    Text(
                        text = "Skip",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            // ViewPager content
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPageContent(
                    page = onboardingPages[page],
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            // Page indicators
            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(onboardingPages.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == pagerState.currentPage) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                }
                            )
                    )
                }
            }
            
            // Navigation buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous button
                if (pagerState.currentPage > 0) {
                    OutlinedButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    ) {
                        Text("Previous")
                    }
                } else {
                    Spacer(modifier = Modifier.width(80.dp))
                }
                
                // Next/Get Started button
                Button(
                    onClick = {
                        if (pagerState.currentPage < onboardingPages.size - 1) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            onOnboardingComplete()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = if (pagerState.currentPage < onboardingPages.size - 1) {
                            "Next"
                        } else {
                            "Get Started"
                        },
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

/**
 * Individual onboarding page content
 */
@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Emoji illustration
        Text(
            text = page.emoji,
            fontSize = 120.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // Title
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Description
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            lineHeight = 24.sp
        )
    }
}

/**
 * Data class for onboarding page
 */
data class OnboardingPage(
    val title: String,
    val description: String,
    val emoji: String
)
