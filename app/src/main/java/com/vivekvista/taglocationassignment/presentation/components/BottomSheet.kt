package com.vivekvista.taglocationassignment.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vivekvista.taglocationassignment.presentation.state.LocationState
import com.vivekvista.taglocationassignment.presentation.viewmodels.LocationViewModel

@Composable
fun BottomSheet(viewModel: LocationViewModel){
    val uiState = viewModel.tagPropertyUIStateFlow.collectAsState()
    BottomSheet(
        uiState = uiState.value,
        onPropertyNameChange = {
            viewModel.onPropertyNameChange(it)
        },
        onSubmit = {
            viewModel.saveProperty()
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
            .padding(top = 38.dp)
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(vertical = 16.dp)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = uiState.propertyName,

                onValueChange = onPropertyNameChange,
                label = {
                    Text(text = "Property Name")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("propertyCoordinates textField"),
                enabled = false,
                value = uiState.propertyCoordinates,
                onValueChange = {},
                label = {
                    Text(text = "Property Coordinates")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("submit button"),
                enabled = uiState.sumbitState,
                onClick = onSubmit
            ) {
                Text(text = "SUBMIT")
            }
        }
    }
}