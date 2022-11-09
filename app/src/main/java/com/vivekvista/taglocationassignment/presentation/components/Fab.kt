package com.vivekvista.taglocationassignment.presentation.components

import android.util.Log
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalFocusManager
import com.vivekvista.taglocationassignment.presentation.state.LocationState
import com.vivekvista.taglocationassignment.presentation.viewmodels.LocationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FAB(
    sheetState: BottomSheetScaffoldState,
    viewModel: LocationViewModel
){
    val uiState = viewModel.locationTagFlow.collectAsState()
    Log.d("TAG", "FAB: is called")
    FAB(
        sheetState = sheetState,
        uiState = uiState.value,
        addMarker = viewModel::onAddMarker,
        removeMarker = viewModel::onReset
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun FAB(
    sheetState: BottomSheetScaffoldState,
    uiState: LocationState,
    addMarker: () -> Unit,
    removeMarker: () -> Unit
) {
    val scope = rememberCoroutineScope()
    FloatingActionButton(onClick = {
        scope.launch {
            if (sheetState.bottomSheetState.isCollapsed) {
                addMarker()
                sheetState.bottomSheetState.expand()
            } else {
                removeMarker()
                sheetState.bottomSheetState.collapse()
            }
        }
    }) {
        val icon = if (!uiState.isMarkerAdded) {
            Icons.Default.Add
        } else {
            Icons.Default.Close
        }
        Icon(
            imageVector = icon,
            contentDescription = "Tag Location"
        )
    }
}
