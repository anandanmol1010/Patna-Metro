package com.app.mypatnametro.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun DisclaimerScreen(
    onDismiss: () -> Unit = {},
    isFirstTime: Boolean = false,
    modifier: Modifier = Modifier
) {
    var hasAccepted by remember { mutableStateOf(!isFirstTime) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 20.dp)
            .padding(start = 20.dp)
            .padding(end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Header Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text(
                text = "Disclaimer",
                style = MaterialTheme.typography.headlineMedium.copy(
                    textDecoration = TextDecoration.Underline
                ),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Icon(
                Icons.Default.Warning,
                contentDescription = "Warning",
                modifier = Modifier.size(50.dp),
                tint = MaterialTheme.colorScheme.error
            )
            
            Text(
                text = "Important Disclaimer",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Please read this carefully before using our app",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
        
        // Main Disclaimer Content
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            DisclaimerCard(
                icon = Icons.Default.Business,
                title = "Unofficial Application",
                content = "This application is NOT an official app of Patna Metro Rail Corporation (PMRC). We are an independent development team creating this app to help commuters with metro information and services."
            )
            
            DisclaimerCard(
                icon = Icons.Default.TrendingUp,
                title = "Data Accuracy",
                content = "While we strive to provide the most accurate and up-to-date information, there may be differences between the data shown in our app and the actual metro services. Real-time information may vary due to technical limitations or data source delays."
            )
            
            DisclaimerCard(
                icon = Icons.Default.Schedule,
                title = "Our Commitment",
                content = "We are committed to providing you with the best possible information at the earliest. Our team works continuously to improve data accuracy and update information as quickly as possible."
            )
            
            DisclaimerCard(
                icon = Icons.Default.Group,
                title = "User Responsibility",
                content = "Users are advised to cross-verify critical information such as train timings, route changes, and service availability with official PMRC sources, especially for important journeys."
            )
            
            DisclaimerCard(
                icon = Icons.Default.Shield,
                title = "Limitation of Liability",
                content = "We shall not be held responsible for any inconvenience, delay, or loss caused due to reliance on the information provided in this application. Use this app as a helpful tool, not as the sole source of metro information."
            )
        }
        
        // Official Sources Section
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.VerifiedUser,
                    contentDescription = "Official Sources",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Official Sources",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Text(
                text = "For official and verified information, please refer to:",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
            
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OfficialSourceRow(Icons.Default.Language, "PMRC Official Website")
                OfficialSourceRow(Icons.Default.Phone, "PMRC Customer Care")
                OfficialSourceRow(Icons.Default.Business, "Metro Station Information Boards")
                OfficialSourceRow(Icons.Default.VolumeUp, "Station Announcements")
            }
        }
        
        // Terms and Conditions
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Description,
                    contentDescription = "Terms",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Terms of Use",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val terms = listOf(
                    "By using this app, you acknowledge that this is an unofficial application",
                    "You understand that data accuracy may vary and should be verified",
                    "You agree to use this app at your own discretion and risk",
                    "We reserve the right to modify or discontinue the service",
                    "Your privacy and data will be handled according to our privacy policy"
                )
                
                terms.forEach { term ->
                    Text(
                        text = "• $term",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }
            }
        }
        
        // Acknowledgment Section
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Checkbox(
                    checked = hasAccepted,
                    onCheckedChange = { hasAccepted = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary
                    )
                )
                
                Text(
                    text = if (isFirstTime) "I have read and understand the disclaimer" else "I have already read and understand the disclaimer",
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            
            Button(
                onClick = onDismiss,
                enabled = hasAccepted,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (hasAccepted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                    disabledContainerColor = MaterialTheme.colorScheme.outline
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.ThumbUp, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "I Understand & Continue",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            if (hasAccepted) {
                Text(
                    text = "Thank you for understanding and using our app responsibly",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun DisclaimerCard(
    icon: ImageVector,
    title: String,
    content: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFeeeeee)
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                RoundedCornerShape(12.dp)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
            
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun OfficialSourceRow(
    icon: ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
    }
}
