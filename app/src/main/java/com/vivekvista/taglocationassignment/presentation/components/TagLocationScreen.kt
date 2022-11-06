package com.vivekvista.taglocationassignment.presentation.tag_location

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.vivekvista.taglocationassignment.common.LocationPermissionsDialog
import com.vivekvista.taglocationassignment.common.LocationUtils
import com.vivekvista.taglocationassignment.presentation.components.BottomSheet
import com.vivekvista.taglocationassignment.presentation.components.FAB
import com.vivekvista.taglocationassignment.presentation.components.LocationPermissionsAndSettingDialogs
import com.vivekvista.taglocationassignment.presentation.components.Map
import com.vivekvista.taglocationassignment.presentation.state.LocationState
import com.vivekvista.taglocationassignment.presentation.viewmodels.LocationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TagLocationScreen(
    viewModel: LocationViewModel,
    fusedLocationProviderClient: FusedLocationProviderClient
) {
    var permissionProvided by remember { mutableStateOf(false)}
    var locationProvided by remember { mutableStateOf(false) }
    var currentLocation by remember { mutableStateOf(LocationUtils.getDefaultLocation()) }

    if (LocationUtils.isLocationPermissionGranted(LocalContext.current)) {
        OnPermissionGranted(
            viewModel = viewModel,
            locationProvided = locationProvided,
            currentLocation = currentLocation
        )
    } else {
        LocationPermissionsDialog(
            onPermissionGranted = {
                permissionProvided = true
            }, onPermissionDenied = {
                permissionProvided = false
            })
    }

    var requestLocationUpdate by remember { mutableStateOf(true)}

    OnPermissionGranted(viewModel = viewModel, locationProvided = locationProvided, currentLocation)

    if(requestLocationUpdate) {
        LocationPermissionsAndSettingDialogs(
            updateCurrentLocation = {
                requestLocationUpdate = false
                LocationUtils.requestLocationResultCallback(fusedLocationProviderClient) { locationResult ->
                    locationResult.lastLocation?.let { location ->
                        Log.d("TAG", "TagLocationScreen: got the last Location")
                        val markerPos = LatLng(location.latitude, location.longitude)
                        viewModel.onLocationCoordinateChange(markerPos)
                         requestLocationUpdate = false
                         locationProvided = true
                         currentLocation = markerPos
                    }

                }
            }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun OnPermissionGranted(
    viewModel: LocationViewModel,
    locationProvided: Boolean,
    currentLocation: LatLng,
) {

    Log.d("Map Method", "TagLocationScreen OnPermissionGranted: $locationProvided")
    //fetching the current state from view model
    val state = viewModel.locationTagFlow.collectAsState()
    val bottomSheetValue =
        if (state.value.isMarkerAdded) BottomSheetValue.Expanded else BottomSheetValue.Collapsed
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(initialValue = bottomSheetValue)
    )
    TagLocation(
        uiState = state.value,
        sheetState = bottomSheetState,
        fab = {
            FAB(sheetState = bottomSheetState, viewModel = viewModel)
        },
        bottomSheet = {
            BottomSheet(viewModel = viewModel)
        },
        map = {
            Map(viewModel = viewModel, currentLocation)
        },
        reset = viewModel::onReset
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun TagLocation(
    uiState: LocationState,
    sheetState: BottomSheetScaffoldState,
    fab: (@Composable () -> Unit),
    bottomSheet: (@Composable () -> Unit),
    map: (@Composable () -> Unit),
    reset: () -> Unit
) {
    val scope = rememberCoroutineScope()

    //onBackPressed
    BackHandler {
        scope.launch {
            reset()
            sheetState.bottomSheetState.collapse()
        }
    }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {
            bottomSheet()
        },
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetGesturesEnabled = false,
        sheetPeekHeight = 35.dp,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = fab
    ) {

        map()

        if (uiState.isLocationSaved) {
            reset()
            Toast.makeText(LocalContext.current, "Location saved", Toast.LENGTH_LONG).show()
        }
    }
}