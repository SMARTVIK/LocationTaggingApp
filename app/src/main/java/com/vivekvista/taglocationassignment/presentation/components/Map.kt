package com.vivekvista.taglocationassignment.presentation.components

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.vivekvista.taglocationassignment.common.LocationUtils
import com.vivekvista.taglocationassignment.presentation.state.LocationState
import com.vivekvista.taglocationassignment.presentation.viewmodels.LocationViewModel

@Composable
fun Map(viewModel: LocationViewModel, currentLocation: LatLng) {
    val locationState = viewModel.locationTagFlow.collectAsState()
    locationState.value.markerPosition = currentLocation
    Map(
        locationState = locationState.value,
        onLatLngChange = {
            viewModel.onLocationCoordinateChange(it)
        }
    )
}

@Composable
internal fun Map(
    locationState: LocationState,
    onLatLngChange: (latLng: LatLng) -> Unit
) {

    Log.d("TAG", "Map: is called")

    //Camera is zoomed in to locate the properties easily
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(locationState.markerPosition, 180f)
    }

    //We are using satellite view for location tagging
    val properties = remember {
        mutableStateOf(MapProperties(mapType = MapType.SATELLITE))
    }

    val uiSettings = remember {
        mutableStateOf(MapUiSettings())
    }

    uiSettings.value = uiSettings.value.copy(scrollGesturesEnabled = !locationState.isMarkerAdded)

    val yPos: Float by animateFloatAsState(
        targetValue = if(locationState.isMarkerAdded) -250f else 0f,
        animationSpec = tween(300, easing = LinearOutSlowInEasing)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer {
            translationY = yPos
        }
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .testTag("Map"),
            properties = properties.value,
            uiSettings = uiSettings.value,
            cameraPositionState = cameraPositionState
        ) {
            val latLong = cameraPositionState.position.target
            onLatLngChange(latLong)


            if (locationState.isMarkerAdded) {
                Marker(
                    tag = "Marker",
                    state = MarkerState(position = latLong),
                )
            }
        }

        Button(onClick = { cameraPositionState.position = CameraPosition.fromLatLngZoom(LocationUtils.getDefaultLocation(),180f) }) {
            Icon(
                modifier = Modifier
                    .testTag("Target")
                    .size(60.dp),
                //Red is more visible than Black
                tint = Color.Red,
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go To Current Location",
            )
        }

        //Once marker is added we change the icon to add icon
        if (!locationState.isMarkerAdded) {
            Icon(
                modifier = Modifier
                    .testTag("Target")
                    .align(Alignment.Center)
                    .size(40.dp),
                //Red is more visible than Black
                tint = Color.Red,
                imageVector = Icons.Default.Add,
                contentDescription = "Location"
            )
        }
    }
}