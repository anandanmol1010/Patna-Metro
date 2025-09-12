package com.app.mypatnametro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.mypatnametro.data.model.UserProfile
import com.app.mypatnametro.ui.components.BottomBarView
import com.app.mypatnametro.viewmodel.UserProfileViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: UserProfileViewModel = viewModel()
    var showEditProfile by remember { mutableStateOf(false) }
    val profile by viewModel.profile
    val themeColor = Color(0xFF1eacfa)

    // Show edit profile screen if editing
    if (showEditProfile) {
        EditProfileScreen(
            viewModel = viewModel,
            onBack = { showEditProfile = false }
        )
    } else {
        // Main profile screen
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.padding(top= 30.dp),
                    title = {
                        Text(
                            text = "",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom=20.dp)
                    .padding(end=20.dp)
                    .padding(start=20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Profile Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        modifier = Modifier.padding(bottom = 30.dp),
                        text = "Profile",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium
                    )
                    // Profile Picture
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Gray.copy(alpha = 0.6f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Picture",
                            modifier = Modifier.size(60.dp),
                            tint = Color.White
                        )
                    }
                    
                    // Name
                    Text(
                        text = profile.fullName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    
                    // Metro Card Number
                    Text(
                        text = profile.metroCardNumber,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    
                    // Edit Profile Button
                    Button(
                        onClick = { showEditProfile = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = themeColor
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Edit Profile",
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Bottom Bar with social links
                BottomBarView(
                    modifier = Modifier.padding(bottom = 0.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: UserProfileViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.profile
    val themeColor = Color(0xFF1eacfa)
    
    // Local state for form fields
    var fullName by remember { mutableStateOf(profile.fullName) }
    var gender by remember { mutableStateOf(profile.gender) }
    var birthday by remember { mutableStateOf(profile.birthday) }
    var phoneNumber by remember { mutableStateOf(profile.phoneNumber) }
    var email by remember { mutableStateOf(profile.email) }
    var metroCardNumber by remember { mutableStateOf(profile.metroCardNumber) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(top = 30.dp)
                .padding(start = 16.dp)
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }
        
        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
                .padding(start = 16.dp)
                .padding(end = 16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Picture and Name
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(Color.Gray.copy(alpha = 0.6f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(50.dp),
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                
                Text(
                    text = fullName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(2.dp))
                
                Text(
                    text = metroCardNumber.ifEmpty { "No Card Number" },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            // Form Fields
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CustomTextField(
                    label = "Full name",
                    value = fullName,
                    onValueChange = { fullName = it }
                )
                
                CustomTextField(
                    label = "Gender",
                    value = gender,
                    onValueChange = { gender = it }
                )
                
                // Birthday DatePicker
                BirthdayDatePicker(
                    selectedDate = birthday,
                    onDateSelected = { birthday = it }
                )
                
                CustomTextField(
                    label = "Phone number",
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it }
                )
                
                CustomTextField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it }
                )
                
                CustomTextField(
                    label = "Metro Card Number",
                    value = metroCardNumber,
                    onValueChange = { metroCardNumber = it }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            
            // Save Button
            Button(
                onClick = {
                    // Update the profile with new values
                    viewModel.updateFullName(fullName)
                    viewModel.updateGender(gender)
                    viewModel.updateBirthday(birthday)
                    viewModel.updatePhoneNumber(phoneNumber)
                    viewModel.updateEmail(email)
                    viewModel.updateMetroCardNumber(metroCardNumber)
                    viewModel.saveProfile()
                    onBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = themeColor
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Save",
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            
//            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF1eacfa),
            focusedLabelColor = Color(0xFF1eacfa),
            unfocusedLabelColor = Color.Gray
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayDatePicker(
    selectedDate: Date,
    onDateSelected: (Date) -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var showDatePicker by remember { mutableStateOf(false) }
    
    OutlinedTextField(
        value = dateFormatter.format(selectedDate),
        onValueChange = { },
        label = { Text("Birthday") },
        modifier = modifier.fillMaxWidth(),
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select Date"
                )
            }
        },
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF1eacfa),
            focusedLabelColor = Color(0xFF1eacfa),
            unfocusedLabelColor = Color.Gray
        )
    )
    
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate.time
        )
        
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            onDateSelected(Date(millis))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserProfileScreenPreview() {
    UserProfileScreen(
        onNavigateBack = {}
    )
}
