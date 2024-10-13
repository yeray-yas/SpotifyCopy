package com.yerayyas.cursofirebaselite.domain.usecases

import com.yerayyas.cursofirebaselite.data.Repository
import javax.inject.Inject

class CanAccessToAppUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(): Boolean {
        val currentVersion = repository.getCurrentVersion()
        val minAllowedVersion = repository.getMinAllowedVersion()

        return isCurrentVersionValid(currentVersion, minAllowedVersion)
    }

    private fun isCurrentVersionValid(currentVersion: List<Int>, minAllowedVersion: List<Int>): Boolean {
        for ((currentPart, minVersionPart) in currentVersion.zip(minAllowedVersion)) {
            if (currentPart != minVersionPart) {
                return currentPart > minVersionPart
            }
        }
        return true
    }
}
