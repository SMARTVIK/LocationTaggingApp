package com.vivekvista.taglocationassignment.presentation.tag_location

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vivekvista.taglocationassignment.presentation.components.BottomSheet
import com.vivekvista.taglocationassignment.presentation.components.FAB
import com.vivekvista.taglocationassignment.presentation.components.Map
import com.vivekvista.taglocationassignment.presentation.state.LocationState
import com.vivekvista.taglocationassignment.presentation.viewmodels.LocationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TagLocationScreen(viewModel: LocationViewModel) {
    val state = viewModel.tagPropertyUIStateFlow.collectAsState()
    val bottomSheetValue = if(state.value.isMarkerAdded) BottomSheetValue.Expanded else BottomSheetValue.Collapsed
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(initialValue = bottomSheetValue)
    )
    TagPropertyScreen(
        uiState = state.value,
        sheetState = bottomSheetState,
        fab = {
            FAB(sheetState = bottomSheetState, viewModel = viewModel)
        },
        bottomSheet = {
            BottomSheet(viewModel = viewModel)
        },
        map = {
            Map(viewModel = viewModel)
        },
        reset = viewModel::reset
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun TagPropertyScreen(
    uiState: LocationState,
    sheetState: BottomSheetScaffoldState,
    fab: (@Composable () -> Unit),
    bottomSheet: (@Composable () -> Unit),
    map: (@Composable () -> Unit),
    reset: () -> Unit
) {
    val scope = rememberCoroutineScope()
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
        sheetPeekHeight = 38.dp,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = fab
    ) {

        map()

        if (uiState.isLocationSaved) {
            reset()
            Toast.makeText(LocalContext.current, "Property is saved", Toast.LENGTH_LONG).show()
        }
    }
}