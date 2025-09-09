package com.app.mypatnametro.data.model


data class MetroStation(
    val name: String,
    val line: String = "East-West", // Default line
    val corridors: List<String>,
    val connections: List<String>
) {
    companion object {
        private val metroNetwork = MetroNetwork()
        
        fun getAllStations(): List<MetroStation> {
            return metroNetwork.stations.values.toList()
        }
        
        fun findRoute(from: String, to: String): List<String>? {
            return metroNetwork.findShortestPath(from, to)
        }
    }
}

class MetroNetwork {
    

    val stations: Map<String, MetroStation> = mapOf(
        // East-West Corridor
        "Danapur Cantonment" to MetroStation(
            name = "Danapur Cantonment",
            corridors = listOf("East-West"),
            connections = listOf("Saguna Mor")
        ),
        "Saguna Mor" to MetroStation(
            name = "Saguna Mor",
            corridors = listOf("East-West"),
            connections = listOf("Danapur Cantonment", "RPS Mor")
        ),
        "RPS Mor" to MetroStation(
            name = "RPS Mor",
            corridors = listOf("East-West"),
            connections = listOf("Saguna Mor", "Patliputra")
        ),
        "Patliputra" to MetroStation(
            name = "Patliputra",
            corridors = listOf("East-West"),
            connections = listOf("RPS Mor", "Rukanpura")
        ),
        "Rukanpura" to MetroStation(
            name = "Rukanpura",
            corridors = listOf("East-West"),
            connections = listOf("Patliputra", "Raja Bazar")
        ),
        "Raja Bazar" to MetroStation(
            name = "Raja Bazar",
            corridors = listOf("East-West"),
            connections = listOf("Rukanpura", "Patna Zoo")
        ),
        "Patna Zoo" to MetroStation(
            name = "Patna Zoo",
            corridors = listOf("East-West"),
            connections = listOf("Raja Bazar", "Vikas Bhawan")
        ),
        "Vikas Bhawan" to MetroStation(
            name = "Vikas Bhawan",
            corridors = listOf("East-West"),
            connections = listOf("Patna Zoo", "Vidyut Bhawan")
        ),
        "Vidyut Bhawan" to MetroStation(
            name = "Vidyut Bhawan",
            corridors = listOf("East-West"),
            connections = listOf("Vikas Bhawan", "Patna Junction")
        ),
        
        // Patna Junction as Interchange Station in Both Corridors
        "Patna Junction" to MetroStation(
            name = "Patna Junction",
            corridors = listOf("East-West", "North-South"),
            connections = listOf("Vidyut Bhawan", "CNLU", "Akashvani")
        ),
        
        "CNLU" to MetroStation(
            name = "CNLU",
            corridors = listOf("East-West"),
            connections = listOf("Patna Junction", "Mithapur")
        ),
        "Mithapur" to MetroStation(
            name = "Mithapur",
            corridors = listOf("East-West"),
            connections = listOf("CNLU", "Ramkrishna Nagar")
        ),
        "Ramkrishna Nagar" to MetroStation(
            name = "Ramkrishna Nagar",
            corridors = listOf("East-West"),
            connections = listOf("Mithapur", "Jaganpur")
        ),
        "Jaganpur" to MetroStation(
            name = "Jaganpur",
            corridors = listOf("East-West"),
            connections = listOf("Ramkrishna Nagar", "Khemni Chak")
        ),
        
        // Khemni Chak as Another Interchange
        "Khemni Chak" to MetroStation(
            name = "Khemni Chak",
            corridors = listOf("East-West", "North-South"),
            connections = listOf("Jaganpur", "Malahi Pakri", "Rajendra Nagar", "Bhoothnath")
        ),

        // North-South Corridor
        "Akashvani" to MetroStation(
            name = "Akashvani",
            corridors = listOf("North-South"),
            connections = listOf("Patna Junction", "Gandhi Maidan")
        ),
        "Gandhi Maidan" to MetroStation(
            name = "Gandhi Maidan",
            corridors = listOf("North-South"),
            connections = listOf("Akashvani", "PMCH")
        ),
        "PMCH" to MetroStation(
            name = "PMCH",
            corridors = listOf("North-South"),
            connections = listOf("Gandhi Maidan", "Patna University")
        ),
        "Patna University" to MetroStation(
            name = "Patna University",
            corridors = listOf("North-South"),
            connections = listOf("PMCH", "Moin Ul Haq Stadium")
        ),
        "Moin Ul Haq Stadium" to MetroStation(
            name = "Moin Ul Haq Stadium",
            corridors = listOf("North-South"),
            connections = listOf("Patna University", "Rajendra Nagar")
        ),
        "Rajendra Nagar" to MetroStation(
            name = "Rajendra Nagar",
            corridors = listOf("North-South"),
            connections = listOf("Moin Ul Haq Stadium", "Malahi Pakri", "Khemni Chak")
        ),
        "Malahi Pakri" to MetroStation(
            name = "Malahi Pakri",
            corridors = listOf("North-South"),
            connections = listOf("Rajendra Nagar", "Khemni Chak")
        ),
        "Bhoothnath" to MetroStation(
            name = "Bhoothnath",
            corridors = listOf("North-South"),
            connections = listOf("Khemni Chak", "Zero Mile")
        ),
        "Zero Mile" to MetroStation(
            name = "Zero Mile",
            corridors = listOf("North-South"),
            connections = listOf("Bhoothnath", "New ISBT")
        ),
        "New ISBT" to MetroStation(
            name = "New ISBT",
            corridors = listOf("North-South"),
            connections = listOf("Zero Mile")
        )
    )
    
    /**
     * Find shortest path between two stations using BFS algorithm
     * 
     * 
     * @param source Starting station name
     * @param destination Ending station name
     * @return List of station names representing the path, or null if no path found
     */
    fun findShortestPath(source: String, destination: String): List<String>? {
        // Check if both stations exist
        if (!stations.containsKey(source) || !stations.containsKey(destination)) {
            return null // Source or Destination not found
        }
        
        // BFS implementation
        val queue = mutableListOf<Pair<String, List<String>>>()
        val visited = mutableSetOf<String>()
        
        queue.add(Pair(source, listOf(source)))
        visited.add(source)
        
        while (queue.isNotEmpty()) {
            val (currentStation, path) = queue.removeFirst()
            
            if (currentStation == destination) {
                return path
            }
            
            stations[currentStation]?.connections?.forEach { nextStation ->
                if (!visited.contains(nextStation)) {
                    visited.add(nextStation)
                    queue.add(Pair(nextStation, path + nextStation))
                }
            }
        }
        
        return null // No path found
    }
    

    fun getAllStationNames(): List<String> {
        return stations.keys.toList().sorted()
    }
    

    fun getInterchangeStations(): List<String> {
        return stations.filter { it.value.corridors.size > 1 }.keys.toList()
    }
}
