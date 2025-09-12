package com.app.mypatnametro.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.mypatnametro.ui.screens.AccountScreen
import com.app.mypatnametro.ui.screens.ContactSupportScreen
import com.app.mypatnametro.ui.screens.HomeScreen
import com.app.mypatnametro.ui.screens.TransactionScreen
import com.app.mypatnametro.ui.screens.RouteNavigationScreen
import com.app.mypatnametro.ui.screens.MapScreen
import com.app.mypatnametro.ui.screens.ParkingLotsScreen
import com.app.mypatnametro.ui.screens.AboutUsScreen
import com.app.mypatnametro.ui.screens.ContactUsScreen
import com.app.mypatnametro.ui.screens.DisclaimerScreen
import com.app.mypatnametro.ui.screens.RateUsScreen
import com.app.mypatnametro.ui.screens.TicketScreen
import com.app.mypatnametro.ui.screens.PatnaMetroInfoScreen
import com.app.mypatnametro.ui.screens.UserProfileScreen
import com.app.mypatnametro.ui.components.ComingSoonView


sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Filled.Home)
    object TopUp : BottomNavItem("topup", "Top Up", Icons.Filled.Payment)
    object Support : BottomNavItem("support", "Support", Icons.Filled.Info)
    object Account : BottomNavItem("account", "Account", Icons.Filled.AccountCircle)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatnaMetroNavigation() {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.TopUp,
        BottomNavItem.Support,
        BottomNavItem.Account
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {

                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                launchSingleTop = true

                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(BottomNavItem.TopUp.route) {
                TransactionScreen()
            }
            composable(BottomNavItem.Support.route) {
                ContactSupportScreen()
            }
            composable(BottomNavItem.Account.route) {
                AccountScreen(
                    onNavigateToProfile = {
                        navController.navigate("user_profile")
                    }
                )
            }
            
            // Additional screens from HomeScreen navigation
            composable("route_planning") {
                RouteNavigationScreen()
            }
            composable("route_navigation") {
                RouteNavigationScreen()
            }
            composable("map_view") {
                MapScreen()
            }
            composable("map") {
                MapScreen()
            }
            composable("parking_lots") {
                ParkingLotsScreen()
            }
            composable("about_us") {
                AboutUsScreen()
            }
            composable("contact_us") {
                ContactUsScreen()
            }
            composable("disclaimer") {
                DisclaimerScreen(
                    onDismiss = {
                        navController.popBackStack()
                    },
                    isFirstTime = false
                )
            }
            composable("rate_us") {
                RateUsScreen(
                    onDismiss = {
                        navController.navigate("home") {
                            popUpTo("home") {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable("ticket") {
                TicketScreen()
            }
            composable("information") {
                PatnaMetroInfoScreen()
            }
            composable("metro_info") {
                PatnaMetroInfoScreen()
            }
            composable("coming_soon") {
                ComingSoonView()
            }
            composable("user_profile") {
                UserProfileScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Homepreview() {
    PatnaMetroNavigation()
}
