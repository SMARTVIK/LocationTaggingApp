package com.vivekvista.taglocationassignment.presentation

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.vivekvista.taglocationassignment.presentation.components.LocationService
import com.vivekvista.taglocationassignment.presentation.tag_location.TagLocationScreen
import com.vivekvista.taglocationassignment.presentation.ui.theme.TagLocationAssignmentTheme
import com.vivekvista.taglocationassignment.presentation.viewmodels.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val locationViewModel: LocationViewModel by viewModels()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var serviceConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            0
        )
        setContent {
            TagLocationAssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TagLocationScreen(locationViewModel, fusedLocationProviderClient)
                }
            }
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("TAG", "onServiceConnected: service connected")
            serviceConnected = true
            val serviceInstance = (service as LocationService.LocalBinder).getService()
            locationViewModel.onLocationCoordinateChange(serviceInstance.currentLocation.value)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceConnected = false
        }
    }

    override fun onResume() {
        super.onResume()
        Intent(this, LocationService::class.java).also { intent ->
            intent.action = LocationService.ACTION_START
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            startService(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }


}