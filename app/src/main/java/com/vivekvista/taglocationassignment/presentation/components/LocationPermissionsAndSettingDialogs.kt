package com.vivekvista.taglocationassignment.presentation.components

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.vivekvista.taglocationassignment.common.LocationPermissionsDialog
import com.vivekvista.taglocationassignment.common.LocationUtils

@Composable
fun LocationPermissionsAndSettingDialogs(
    updateCurrentLocation: () -> Unit,
) {
    var requestLocationSetting by remember { mutableStateOf(false) }

    if(LocationUtils.isLocationPermissionGranted(LocalContext.current)) {
        SideEffect {
            requestLocationSetting = true
        }
    } else {
        LocationPermissionsDialog(
            onPermissionGranted = {
                requestLocationSetting = true
            },
            onPermissionDenied = {
                requestLocationSetting = true
            },
        )
    }

    if (requestLocationSetting) {

        LocationSettingDialog(
            onSuccess = {
                requestLocationSetting = false
                updateCurrentLocation()
            },
            onFailure = {
                requestLocationSetting = false
                updateCurrentLocation()
            },
        )
    }
}

