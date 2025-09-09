package com.app.mypatnametro.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.mypatnametro.data.model.MetroNetwork
import com.app.mypatnametro.ui.theme.PatnaMetroPrimary
import com.app.mypatnametro.viewmodel.RouteNavigationViewModel


@Composable
fun RouteNavigationScreen(
    modifier: Modifier = Modifier,
    viewModel: RouteNavigationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
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
            fontWeight = FontWeight.Bold
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
    
    // Show route results if available
    if (uiState.showRouteResults && uiState.route.isNotEmpty()) {
        RouteDetailView(
            source = uiState.source,
            destination = uiState.destination,
            route = uiState.route,
            onDismiss = { viewModel.clearRoute() }
        )
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
private fun RouteDetailView(
    source: String,
    destination: String,
    route: List<String>,
    onDismiss: () -> Unit
) {
    val interchangeCount = route.count { it == "Patna Junction" || it == "Khemni Chak" }
    
    // Full screen overlay
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onDismiss() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clickable { }, // Prevent dismissing when clicking on card
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header with close button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PatnaMetroPrimary)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Route Details",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    
                    IconButton(onClick = onDismiss) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }
                
                // Route info section
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Source to destination
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        RouteInfoView(
                            icon = "📍",
                            title = source,
                            info = ""
                        )
                        Icon(Icons.Default.ArrowForward, contentDescription = "To")
                        RouteInfoView(
                            icon = "📍", 
                            title = destination,
                            info = ""
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
                            modifier = Modifier.weight(1f)
                        )
                        RouteInfoView(
                            icon = "⏱️",
                            title = "Mins", 
                            info = "0",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        RouteInfoView(
                            icon = "🚇",
                            title = "Stations",
                            info = "${route.size}",
                            modifier = Modifier.weight(1f)
                        )
                        RouteInfoView(
                            icon = "🔄",
                            title = "Line Change",
                            info = "$interchangeCount",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                // Route steps
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(route.size) { index ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Station number
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(PatnaMetroPrimary, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            
                            // Station name
                            Text(
                                text = route[index],
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        
                        // Connection line (except for last item)
                        if (index < route.size - 1) {
                            Box(
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .width(2.dp)
                                    .height(24.dp)
                                    .background(PatnaMetroPrimary)
                            )
                        }
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
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = icon, style = MaterialTheme.typography.titleMedium)
            if (info.isNotEmpty()) {
                Text(
                    text = info,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
