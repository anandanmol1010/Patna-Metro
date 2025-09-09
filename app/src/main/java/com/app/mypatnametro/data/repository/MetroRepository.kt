package com.app.mypatnametro.data.repository

import com.app.mypatnametro.data.model.MetroStation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MetroRepository @Inject constructor() {
    
    fun getAllStations(): Flow<List<MetroStation>> {
        return flowOf(MetroStation.getAllStations())
    }
    
    fun findRoute(from: String, to: String): Flow<List<String>?> {
        val route = MetroStation.findRoute(from, to)
        return flowOf(route)
    }
    
    fun getStationsByLine(line: String): Flow<List<MetroStation>> {
        val stations = MetroStation.getAllStations().filter { it.line == line }
        return flowOf(stations)
    }
}
