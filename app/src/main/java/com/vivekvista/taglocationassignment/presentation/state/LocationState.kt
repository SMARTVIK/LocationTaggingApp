package com.vivekvista.taglocationassignment.presentation.state

import com.vivekvista.taglocationassignment.common.Constants.INITIAL_LOCATION
import com.google.android.gms.maps.model.LatLng

data class LocationState(
    val markerPosition: LatLng = INITIAL_LOCATION,
    val isMarkerAdded: Boolean = false,
    val isLocationSaved: Boolean = false,
    val locationName: String = "",
    val locationCoordinates: String = "${markerPosition.latitude},${markerPosition.longitude}"
) {
    val sumbitState: Boolean
        get() {
            return locationName.isNotEmpty()
        }
}
