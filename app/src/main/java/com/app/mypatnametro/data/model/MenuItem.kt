package com.app.mypatnametro.data.model


data class MenuItem(
    val mainLine: String,
    val secondLine: String,
    val moduleID: Int,
    val imageUrl: String
)

object MenuItems {
    val items = listOf(
        MenuItem(
            mainLine = "Route",
            secondLine = "Find your route",
            moduleID = 0,
            imageUrl = "routeimg"
        ),
        MenuItem(
            mainLine = "Map",
            secondLine = "Check your route",
            moduleID = 1,
            imageUrl = "mapimg"
        ),
        MenuItem(
            mainLine = "Fare",
            secondLine = "Know your fare",
            moduleID = 2,
            imageUrl = "ticketimg"
        ),
        MenuItem(
            mainLine = "Parking",
            secondLine = "Looking for parking",
            moduleID = 3,
            imageUrl = "queryimg"
        )
    )
}

object MenuSubItems {
    val items = listOf(
        MenuItem(
            mainLine = "Share",
            secondLine = "",
            moduleID = 4,
            imageUrl = "share"
        ),
        MenuItem(
            mainLine = "Rate Us",
            secondLine = "",
            moduleID = 5,
            imageUrl = "rate_us"
        ),
        MenuItem(
            mainLine = "About Us",
            secondLine = "",
            moduleID = 6,
            imageUrl = "about_us"
        ),
        MenuItem(
            mainLine = "Disclaimer",
            secondLine = "",
            moduleID = 7,
            imageUrl = "disclaimer"
        ),
        MenuItem(
            mainLine = "Information",
            secondLine = "",
            moduleID = 8,
            imageUrl = "information"
        ),
        MenuItem(
            mainLine = "Contact Us",
            secondLine = "",
            moduleID = 9,
            imageUrl = "contact_us"
        )
    )
}
