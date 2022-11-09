package com.vivekvista.taglocationassignment.presentation.components

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.vivekvista.taglocationassignment.presentation.state.LocationState
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class BottomSheetKtTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun locationCoordinatesTextFieldIsDisabledForEnteringText() {
        composeRule.setContent {
            BottomSheet(
                uiState = LocationState(locationName = "South Delhi"),
                onLocationChanged = {}
            ) {
            }
        }

        composeRule
            .onNodeWithTag("location coordinates textField")
            .assertIsNotEnabled()
    }

    @Test
    fun submitButtonEnabledWhenEnterLocationNameValue() {
        composeRule.setContent {
            BottomSheet(
                uiState = LocationState(locationName = "India Gate"),
                onLocationChanged = {}
            ) {

            }
        }
        composeRule
            .onNodeWithTag("Submit Button")
            .assertIsEnabled()
    }

    @Test
    fun submitButtonDisabledWhenEnterLocationNameIsBlank() {
        composeRule.setContent {
            BottomSheet(
                uiState = LocationState(locationName = "India Gate"),
                onLocationChanged = {}
            ) {

            }
        }
        composeRule
            .onNodeWithTag("Submit Button")
            .assertIsEnabled()
    }

}