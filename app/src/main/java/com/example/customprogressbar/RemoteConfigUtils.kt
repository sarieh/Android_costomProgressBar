package com.example.customprogressbar

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object RemoteConfigUtils {

    private lateinit var remoteConfig: FirebaseRemoteConfig

    private val DEFAULTS: HashMap<String, Any> =
        hashMapOf(
            RemoteConfigConstants.RED.getPair(),
            RemoteConfigConstants.BLUE.getPair(),
            RemoteConfigConstants.YELLOW.getPair(),
            RemoteConfigConstants.GREEN.getPair(),
            RemoteConfigConstants.MAIN.getPair(),
        )

    fun init() {
        setRemoteConfig()
    }

    fun getRemoteConfig(): FirebaseRemoteConfig {
        return remoteConfig
    }

    private fun setRemoteConfig() {
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) 0 else 3600 //for fast fetching in the debugging mode
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(DEFAULTS)

        fetchAndActivate()
    }

    private fun fetchAndActivate() {
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) remoteConfig.activate()
        }
    }

    enum class RemoteConfigConstants(val key: String, val value: Any) {
        RED("red", 5),
        BLUE("blue", 4),
        GREEN("green", 3),
        YELLOW("yellow", 2),
        MAIN("main", 10);

        fun getPair(): Pair<String, Any> {
            return key to value
        }
    }
}