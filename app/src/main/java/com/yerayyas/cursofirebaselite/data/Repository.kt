package com.yerayyas.cursofirebaselite.data

import android.content.pm.PackageManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.yerayyas.cursofirebaselite.CursoFirebaseLiteApp.Companion.context
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) {

    companion object {
        const val MIN_VERSION = "min_version"
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

