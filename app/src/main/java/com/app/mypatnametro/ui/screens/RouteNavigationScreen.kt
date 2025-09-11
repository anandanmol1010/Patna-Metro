package com.app.mypatnametro.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.mypatnametro.R
import com.app.mypatnametro.data.model.MetroNetwork
import com.app.mypatnametro.ui.theme.PatnaMetroPrimary
import com.app.mypatnametro.viewmodel.RouteNavigationViewModel


@Composable
fun RouteNavigationScreen(
    modifier: Modifier = Modifier,
    viewModel: RouteNavigationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Show route detail screen if route is found
    if (uiState.showRouteResults && uiState.route.isNotEmpty()) {
        RouteDetailScreen(
            source = uiState.source,
            destination = uiState.destination,
            route = uiState.route,
            onBack = { viewModel.clearRoute() }
        )
    } else {
        // Main route search screen
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Title
            Text(
                text = "Find Your Route",
                style = MaterialTheme.typography.headlineLarge,
                color = PatnaMetroPrimary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 60.dp)
            )
            
            // Source and Destination fields
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                SearchableTextField(
                    title = "Select Source",
                    text = uiState.source,
                    onTextChange = { viewModel.updateSource(it) },
                    options = uiState.stations,
                    showDropdown = uiState.showSourceDropdown,
                    onDropdownToggle = { viewModel.toggleSourceDropdown(it) },
                    onOtherDropdownClose = { viewModel.toggleDestinationDropdown(false) }
                )
                
                SearchableTextField(
                    title = "Select Destination", 
                    text = uiState.destination,
                    onTextChange = { viewModel.updateDestination(it) },
                    options = uiState.stations,
                    showDropdown = uiState.showDestinationDropdown,
                    onDropdownToggle = { viewModel.toggleDestinationDropdown(it) },
                    onOtherDropdownClose = { viewModel.toggleSourceDropdown(false) }
                )
            }
            
            // Find Route Button
            Button(
                onClick = { viewModel.findRoute() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                enabled = uiState.source.isNotEmpty() && uiState.destination.isNotEmpty()
            ) {
                Text(
                    text = "Find Route",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun SearchableTextField(
    title: String,
    text: String,
    onTextChange: (String) -> Unit,
    options: List<String>,
    showDropdown: Boolean,
    onDropdownToggle: (Boolean) -> Unit,
    onOtherDropdownClose: () -> Unit
) {
    val filteredOptions = if (text.isEmpty()) {
        options
    } else {
        options.filter { it.lowercase().contains(text.lowercase()) }
    }
    
    Column {
        OutlinedTextField(
            value = text,
            onValueChange = { 
                onTextChange(it)
                onDropdownToggle(it.isNotEmpty())
            },
            label = { Text(title) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(onClick = { 
                        onTextChange("")
                        onDropdownToggle(false)
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PatnaMetroPrimary,
                focusedLabelColor = PatnaMetroPrimary
            )
        )
        
        if (showDropdown && filteredOptions.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                LazyColumn {
                    items(filteredOptions) { option ->
                        Text(
                            text = option,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onTextChange(option)
                                    onDropdownToggle(false)
                                    onOtherDropdownClose()
                                }
                                .padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        if (option != filteredOptions.last()) {
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RouteDetailScreen(
    source: String,
    destination: String,
    route: List<String>,
    onBack: () -> Unit
) {
    // Define line stations
    val redLineStations = listOf(
        "Danapur Cantonment", "Saguna Mor", "RPS Mor", "Patliputra", "Rukanpura",
        "Raja Bazar", "Patna Zoo", "Vikas Bhawan", "Vidyut Bhawan", "Patna Junction",
        "CNLU", "Mithapur", "Ramkrishna Nagar", "Jaganpur", "Khemni Chak"
    )
    
    val blueLineStations = listOf(
        "Patna Junction", "Akashvani", "Gandhi Maidan", "PMCH", "Patna University",
        "Moin Ul Haq Stadium", "Rajendra Nagar", "Malahi Pakri", "Bhoothnath",
        "Zero Mile", "New ISBT", "Khemni Chak"
    )
    
    val interchangeStations = listOf("Patna Junction", "Khemni Chak")
    
    // Function to determine current line for a station
    fun getCurrentLine(station: String, nextStation: String?): String {
        return when {
            nextStation == null -> "red"
            redLineStations.contains(station) && redLineStations.contains(nextStation) -> "red"
            blueLineStations.contains(station) && blueLineStations.contains(nextStation) -> "blue"
            // Handle interchange transitions
            station == "Patna Junction" && blueLineStations.contains(nextStation) -> "blue"
            station == "Khemni Chak" && blueLineStations.contains(nextStation) -> "blue"
            else -> "red"
        }
    }
    
    // Function to check if there's a line change at current station
    fun isLineChange(currentIndex: Int): Boolean {
        if (currentIndex == 0 || currentIndex >= route.size - 1) return false
        
        val prevStation = route[currentIndex - 1]
        val currentStation = route[currentIndex]
        val nextStation = route[currentIndex + 1]
        
        val prevLine = getCurrentLine(prevStation, currentStation)
        val nextLine = getCurrentLine(currentStation, nextStation)
        
        return prevLine != nextLine && interchangeStations.contains(currentStation)
    }
    
    // Calculate actual line changes in the route
    val actualInterchangeCount = (0 until route.size).count { index ->
        isLineChange(index)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PatnaMetroPrimary)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            
            Text(
                text = "Route Details",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.width(48.dp)) // Balance the layout
        }
        
        // Route info section
        Column(
            modifier = Modifier
                .background(PatnaMetroPrimary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Source to destination
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RouteInfoView(
                    icon = "📍",
                    title = source,
                    info = "",
                    isOnBlueBackground = false,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    Icons.Default.ArrowForward, 
                    contentDescription = "To",
                    tint = Color.White
                )
                RouteInfoView(
                    icon = "📍", 
                    title = destination,
                    info = "",
                    isOnBlueBackground = false,
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Route statistics
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RouteInfoView(
                    icon = "💰",
                    title = "Rupees",
                    info = "0",
                    modifier = Modifier.weight(1f),
                    isOnBlueBackground = true
                )
                RouteInfoView(
                    icon = "⏱️",
                    title = "Mins", 
                    info = "0",
                    modifier = Modifier.weight(1f),
                    isOnBlueBackground = true
                )
            }
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RouteInfoView(
                    icon = "🚇",
                    title = "station",
                    info = "${route.size}",
                    modifier = Modifier.weight(1f),
                    isOnBlueBackground = true
                )
                RouteInfoView(
                    icon = "🔄",
                    title = "Line change",
                    info = "$actualInterchangeCount",
                    modifier = Modifier.weight(1f),
                    isOnBlueBackground = true
                )
            }
        }
        
        // Route steps
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(route.size) { index ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        // Station number
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(PatnaMetroPrimary, RoundedCornerShape(5.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        // Station name with interchange indicator (only when line changes)
                        val currentStation = route[index]
                        val displayText = if (isLineChange(index)) {
                            "$currentStation (Interchange)"
                        } else {
                            currentStation
                        }
                        
                        Text(
                            text = displayText,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    // Connection line with appropriate track image (except for last item)
                    if (index < route.size - 1) {
                        val currentStation = route[index]
                        val nextStation = route[index + 1]
                        val currentLine = getCurrentLine(currentStation, nextStation)
                        
                        val trackImage = when (currentLine) {
                            "blue" -> R.drawable.blue_track
                            "red" -> R.drawable.red_track
                            else -> R.drawable.red_track
                        }
                        
                        Image(
                            painter = painterResource(id = trackImage),
                            contentDescription = "Route connection - $currentLine line",
                            modifier = Modifier
                                .padding(start = 28.dp) // Align with station number center
                                .width(25.dp)
                                .height(40.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RouteInfoView(
    icon: String,
    title: String,
    info: String,
    modifier: Modifier = Modifier,
    isOnBlueBackground: Boolean = false
) {
    val backgroundColor = if (isOnBlueBackground) {
        Color.White.copy(alpha = 0.2f)
    } else {
        Color.Transparent
    }
    
    val textColor = Color.Black
    
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = icon, 
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            if (info.isNotEmpty()) {
                Text(
                    text = info,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
            Text(
                text = title,
                style = if (isOnBlueBackground) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyLarge,
                color = textColor
            )
        }
    }
}

