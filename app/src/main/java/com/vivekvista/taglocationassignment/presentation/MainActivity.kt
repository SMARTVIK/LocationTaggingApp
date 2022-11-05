package com.vivekvista.taglocationassignment.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vivekvista.taglocationassignment.presentation.tag_location.TagLocationScreen
import com.vivekvista.taglocationassignment.presentation.ui.theme.TagLocationAssignmentTheme
import com.vivekvista.taglocationassignment.presentation.viewmodels.LocationViewModel

class MainActivity : ComponentActivity() {


    private val locationViewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TagLocationAssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TagLocationScreen(locationViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TagLocationAssignmentTheme {
        Greeting("Android")
    }
}