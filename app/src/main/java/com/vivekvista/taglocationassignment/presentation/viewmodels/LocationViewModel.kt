package com.vivekvista.taglocationassignment.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivekvista.taglocationassignment.common.Constants.INITIAL_LOCATION
import com.google.android.gms.maps.model.LatLng
import com.vivekvista.taglocationassignment.domain.model.LocModel
import com.vivekvista.taglocationassignment.domain.use_case.CheckIfLocationExistUseCase
import com.vivekvista.taglocationassignment.domain.use_case.SaveLocationUseCase
import com.vivekvista.taglocationassignment.presentation.state.LocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
/*
* This ViewModel is created to manage the UI state of the application
* We have put our business logic inside the use_case class
* */
@HiltViewModel
class LocationViewModel @Inject constructor(
    private val saveLocationUseCase: SaveLocationUseCase,
    private val checkIfLocationExistUseCase: CheckIfLocationExistUseCase
) : ViewModel() {

    private val locationName = MutableStateFlow("")
    private val locationCoordinates = MutableStateFlow(INITIAL_LOCATION)
    private val addMarkerFlow = MutableStateFlow(false)
    private val locationSavedFlow = MutableStateFlow(false)


    fun onLocationChange(propertyName: String) {
        locationName.value = propertyName
    }

    fun onLocationCoordinateChange(latLng: LatLng) {
        locationCoordinates.value = latLng
        checkIfThisLocationIsAlreadySaved(latLng)
    }

    private fun checkIfThisLocationIsAlreadySaved(latLng: LatLng) {
             viewModelScope.launch {
                 val deferredValue = viewModelScope.async { checkIfLocationExistUseCase(latLng) }
                 val result = deferredValue.await()
                 if (result != null) {
                     Log.d("RESULT", "checkIfThisLocationIsAlreadySaved: (${latLng.latitude},${latLng.longitude})")
                     locationName.value = result.locationName
                 }
             }
    }

    fun onAddMarker(){
        addMarkerFlow.value = true
    }

    fun onReset(){
        addMarkerFlow.value = false
        locationSavedFlow.value = false
        locationName.value = ""
    }

    val locationTagFlow: StateFlow<LocationState> =
        combine(
            locationName,
            locationCoordinates,
            addMarkerFlow,
            locationSavedFlow
        ) { locationName, locationCoordinates, addMarker, onLocationSavedFlow ->
            LocationState(
                locationName = locationName,
                markerPosition = locationCoordinates,
                isMarkerAdded = addMarker,
                isLocationSaved = onLocationSavedFlow
            )
        }.stateIn(
            initialValue = LocationState(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000)
        )

    /*
    * This is function which saves the location-name and coordinates
    * in db in background thread
    * */
    fun saveLocation(){
        viewModelScope.launch {
            val state = locationTagFlow.value
            withContext(Dispatchers.IO){
                saveLocationUseCase(
                    LocModel(
                        locationName = state.locationName,
                        locationCoordinates = state.locationCoordinates
                    )
                )
            }
            locationSavedFlow.value = true
        }
    }

}