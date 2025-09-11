package com.app.mypatnametro.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.mypatnametro.R


@Composable
fun PatnaMetroInfoScreen(modifier: Modifier = Modifier) {
    var selectedLine by remember { mutableIntStateOf(0) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 20.dp),
            text = "Metro Information",
            style = MaterialTheme.typography.headlineMedium.copy(
                textDecoration = TextDecoration.Underline
            ),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // About My Patna Metro Section
        InfoSection(
            icon = Icons.Default.Info,
            title = "About My Patna Metro",
            content = "My Patna Metro is a rapid transit system under construction by My Patna Metro Rail Corporation Limited (PMRCL). The metro is expected to be operational by August 15, 2025, in time for Independence Day. Phase 1 covers approximately 31 km across two corridors with 24-26 planned stations. The project is funded jointly by State Government (20%), Central Government (20%), and 60% loan from Japan International Cooperation Agency (JICA)."
        )
        
        // Current Status Section
        InfoSection(
            icon = Icons.Default.Construction,
            title = "Current Status",
            content = "The priority elevated section of the Blue Line (approximately 6.5 km) is nearly complete and set to begin operations by August 15, 2025. This section includes five key stations: Malahi Pakri, Khemni Chak, Bhootnath, Zero Mile, and New ISBT. Construction progress is above 90% in these areas, with final finishes and trial runs currently underway."
        )
        
        // Metro Routes Section
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Map,
                    contentDescription = "Routes",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Metro Routes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Line Selection Buttons
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                LineButton(
                    title = "Red Line",
                    subtitle = "East-West",
                    isSelected = selectedLine == 0,
                    color = Color.Red,
                    onClick = { selectedLine = 0 },
                    modifier = Modifier.weight(1f)
                )
                
                LineButton(
                    title = "Blue Line",
                    subtitle = "North-South",
                    isSelected = selectedLine == 1,
                    color = Color(0xFF235DE7),
                    onClick = { selectedLine = 1 },
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Line Details
            when (selectedLine) {
                0 -> RedLineView()
                1 -> BlueLineView()
            }
        }
        
        // Statistics Section
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFeeeeee)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Project Statistics",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                StatisticsRow(
                    corridor = "East-West (Red)",
                    length = "14.5 km",
                    structure = "Elevated & Underground",
                    stations = "12"
                )
                
                HorizontalDivider()
                
                StatisticsRow(
                    corridor = "North-South (Blue)",
                    length = "16.5 km",
                    structure = "Elevated",
                    stations = "14"
                )
            }
        }
        
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun InfoSection(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    content: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge,
//            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 24.sp
        )
    }
}

@Composable
private fun LineButton(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) color else color.copy(alpha = 0.1f),
            contentColor = if (isSelected) Color.White else color
        ),
        shape = RoundedCornerShape(
            topStart = if (title == "Red Line") 10.dp else 0.dp,
            bottomStart = if (title == "Red Line") 10.dp else 0.dp,
            topEnd = if (title == "Blue Line") 10.dp else 0.dp,
            bottomEnd = if (title == "Blue Line") 10.dp else 0.dp
        ),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun RedLineView() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Red Line Map
        Image(
            painter = painterResource(id = R.drawable.red_line),
            contentDescription = "Red Line Map",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Fit
        )
        
        Text(
            text = "Connecting major areas from East to West across Patna",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun BlueLineView() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Blue Line Map
        Image(
            painter = painterResource(id = R.drawable.blue_line),
            contentDescription = "Blue Line Map",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Fit
        )
        
        Text(
            text = "Priority section: Malahi Pakri to New ISBT (Opening August 2025)",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun StatisticsRow(
    corridor: String,
    length: String,
    structure: String,
    stations: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = corridor,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatItem("Length", length)
            StatItem("Structure", structure)
            StatItem("Stations", stations)
        }
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}
