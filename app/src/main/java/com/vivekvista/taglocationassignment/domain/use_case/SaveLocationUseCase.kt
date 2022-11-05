package com.vivekvista.taglocationassignment.domain.use_case


import com.vivekvista.taglocationassignment.domain.model.Property
import com.vivekvista.taglocationassignment.domain.repository.LocationRepository
import javax.inject.Inject
/*
* This use case handles the business logic for a single action
* */
class SaveLocationUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(location: Property) {
        repository.saveLocation(location)
    }
}