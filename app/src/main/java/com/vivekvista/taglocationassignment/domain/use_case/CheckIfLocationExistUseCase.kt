package com.vivekvista.taglocationassignment.domain.use_case

import com.google.android.gms.maps.model.LatLng
import com.vivekvista.taglocationassignment.data.local.model.LocationEntity
import com.vivekvista.taglocationassignment.domain.repository.LocationRepository
import javax.inject.Inject

class CheckIfLocationExistUseCase @Inject constructor(
    private val repository: LocationRepository
) {

    suspend operator fun invoke(location: LatLng): LocationEntity {
        return repository.getLocationByCoordinates(location.latitude, location.longitude)
    }
}