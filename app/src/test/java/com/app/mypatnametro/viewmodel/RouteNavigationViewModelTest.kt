package com.app.mypatnametro.viewmodel

import com.app.mypatnametro.data.repository.MetroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class RouteNavigationViewModelTest {

    @Mock
    private lateinit var metroRepository: MetroRepository

    private lateinit var viewModel: RouteNavigationViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        
        // Mock repository responses
        whenever(metroRepository.getAllStations()).thenReturn(
            flowOf(emptyList())
        )
        
        viewModel = RouteNavigationViewModel(metroRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updateSource updates source in UI state`() = runTest {
        val testSource = "Patna Junction"
        
        viewModel.updateSource(testSource)
        
        assertEquals(testSource, viewModel.uiState.value.source)
    }

    @Test
    fun `updateDestination updates destination in UI state`() = runTest {
        val testDestination = "Kankarbagh"
        
        viewModel.updateDestination(testDestination)
        
        assertEquals(testDestination, viewModel.uiState.value.destination)
    }

    @Test
    fun `findRoute sets loading state and finds route`() = runTest {
        val testRoute = listOf("Patna Junction", "Gandhi Maidan", "Kankarbagh")
        whenever(metroRepository.findRoute("Patna Junction", "Kankarbagh"))
            .thenReturn(flowOf(testRoute))
        
        viewModel.updateSource("Patna Junction")
        viewModel.updateDestination("Kankarbagh")
        
        viewModel.findRoute()
        
        assertEquals(testRoute, viewModel.uiState.value.route)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `clearRoute resets all state`() = runTest {
        viewModel.updateSource("Test Source")
        viewModel.updateDestination("Test Destination")
        
        viewModel.clearRoute()
        
        val state = viewModel.uiState.value
        assertEquals("", state.source)
        assertEquals("", state.destination)
        assertTrue(state.route.isEmpty())
        assertFalse(state.showRouteResults)
    }
}
