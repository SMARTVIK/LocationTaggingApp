package com.vivekvista.taglocationassignment.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vivekvista.taglocationassignment.presentation.state.LocationState
import com.vivekvista.taglocationassignment.presentation.ui.theme.bottomSheetBackground
import com.vivekvista.taglocationassignment.presentation.ui.theme.buttonBackground
import com.vivekvista.taglocationassignment.presentation.viewmodels.LocationViewModel

@Composable
fun BottomSheet(viewModel: LocationViewModel){
    val uiState = viewModel.locationTagFlow.collectAsState()
    BottomSheet(
        uiState = uiState.value,
        onPropertyNameChange = {
            viewModel.onLocationChange(it)
        },
        onSubmit = {
            viewModel.saveLocation()
        }
    )
}


@Composable
internal fun BottomSheet(
    uiState: LocationState,
    onPropertyNameChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.bottomSheetBackground)
                .padding(vertical = 16.dp)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = uiState.locationName,

                onValueChange = onPropertyNameChange,
                label = {
                    Text(text = "Location Name")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("location coordinates textField"),
                enabled = false,
                value = uiState.locationCoordinates,
                onValueChange = {},
                label = {
                    Text(text = "Location Coordinates")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("Submit Button"),
                enabled = uiState.sumbitState,
                onClick = onSubmit,
            ) {
                Text(
                    text = "SUBMIT",
                )
            }
        }
    }
}

@Preview
@Composable
internal fun BottomSheetPreview() {

}