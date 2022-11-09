package com.vivekvista.taglocationassignment.presentation.components

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.vivekvista.taglocationassignment.R
import com.vivekvista.taglocationassignment.common.LocationUtils
import com.vivekvista.taglocationassignment.data.local.repository.DefaultLocationClient
import com.vivekvista.taglocationassignment.domain.repository.LocationClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationService : Service() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    lateinit var client: LocationClient
    val binder = LocalBinder()
    var currentLocation = mutableStateOf(LocationUtils.getDefaultLocation())

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        client = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStart(intent: Intent?, startId: Int) {
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        super.onStart(intent, startId)
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    private fun start() {

        Log.d("TAG", "start: service started")

        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("tracking location...")
            .setContentText("Location: null")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        client.getLocationUpdates(10000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                val lat = location.latitude.toString()
                val long = location.longitude.toString()
                currentLocation.value = LatLng(location.latitude,location.longitude)
                val updatedNotification = notification.setContentText(
                    "Location: ($lat, $long)"
                )
                notificationManager.notify(1, updatedNotification.build())
            }.launchIn(serviceScope)

        startForeground(1, notification.build())

    }

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): LocationService = this@LocationService
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }

}