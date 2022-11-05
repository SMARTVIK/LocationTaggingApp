package com.vivekvista.taglocationassignment.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivekvista.taglocationassignment.common.Constants.INITIAL_LOCATION
import com.google.android.gms.maps.model.LatLng
import com.vivekvista.taglocationassignment.domain.model.Property
import com.vivekvista.taglocationassignment.domain.use_case.SaveLocationUseCase
import com.vivekvista.taglocationassignment.presentation.state.LocationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
/*
* This ViewModel is created to manage the UI state of the application
* We have put our business logic inside the use_case class
* */
class LocationViewModel @Inject constructor(
    private val saveLocationUseCase: SaveLocationUseCase
) : ViewModel() {

    private val propertyNameFlow = MutableStateFlow("")
    private val propertyCoordinatesFlow = MutableStateFlow(INITIAL_LOCATION)
    private val addMarkerFlow = MutableStateFlow(false)
    private val propertySavedFlow = MutableStateFlow(false)


    fun onPropertyNameChange(propertyName: String) {
        propertyNameFlow.value = propertyName
    }

    fun onPropertyCoordinatesChange(latLng: LatLng) {
        propertyCoordinatesFlow.value = latLng
    }

    fun addMarker(){
        addMarkerFlow.value = true
    }

    fun reset(){
        addMarkerFlow.value = false
        propertySavedFlow.value = false
        propertyNameFlow.value = ""
    }

    val tagPropertyUIStateFlow: StateFlow<LocationState> =
        combine(
            propertyNameFlow,
            propertyCoordinatesFlow,
            addMarkerFlow,
            propertySavedFlow
        ) { propertyName, propertyCoordinates, addMarker, propertySaved ->
            LocationState(
                propertyName = propertyName,
                markerPosition = propertyCoordinates,
                isMarkerAdded = addMarker,
                isLocationSaved = propertySaved
            )
        }.stateIn(
            initialValue = LocationState(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

    fun saveProperty(){
        viewModelScope.launch {
            val state = tagPropertyUIStateFlow.value
            withContext(Dispatchers.IO){
                saveLocationUseCase(
                    Property(
                        propertyName = state.propertyName,
                        propertyCoordinates = state.propertyCoordinates
                    )
                )
            }
            propertySavedFlow.value = true
        }
    }

}