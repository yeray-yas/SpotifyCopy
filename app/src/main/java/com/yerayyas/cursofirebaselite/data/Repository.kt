package com.yerayyas.cursofirebaselite.data

import android.content.Context
import android.content.pm.PackageManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.yerayyas.cursofirebaselite.CursoFirebaseLiteApp
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class Repository {

    private val context= CursoFirebaseLiteApp.context

    companion object {
        const val MIN_VERSION = "min_version"
        const val ONE_HOUR_IN_SECONDS = 3600L
    }

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig.apply {
        setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = ONE_HOUR_IN_SECONDS })
        fetchAndActivate()
    }

    suspend fun getMinAllowedVersion(): List<Int> {
        remoteConfig.fetch(0)
        remoteConfig.activate().await()
        return parseVersion(remoteConfig.getString(MIN_VERSION))
    }

    fun getCurrentVersion(): List<Int> {
        return try {
            val versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            parseVersion(versionName)
        } catch (e: PackageManager.NameNotFoundException) {
            // Handle case where the package name is not found
            listOf(0, 0, 0)
        } catch (e: Exception) {
            // Handle any other exceptions gracefully
            listOf(0, 0, 0)
        }
    }

    private fun parseVersion(versionString: String): List<Int> {
        return if (versionString.isBlank()) {
            listOf(0, 0, 0)
        } else {
            versionString.split(".")
                .map { it.toIntOrNull() ?: 0 } // Converts each part to Int, defaulting to 0 if conversion fails
        }
    }
}

