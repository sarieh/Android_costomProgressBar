package com.example.customprogressbar

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.customprogressbar.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var bar: ProgressBar
    private lateinit var progressDrawable: ProgressDrawable
    private lateinit var analytic: FirebaseAnalytics
    private lateinit var config: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //prints the token for testing purposes
        FirebaseInstallations.getInstance().getToken(true)
            .addOnCompleteListener {
                if (it.isSuccessful) Log.e("Installations: ", it.result.token)
            }

        analytic = FirebaseAnalytics.getInstance(this)
        RemoteConfigUtils.init()
        config = RemoteConfigUtils.getRemoteConfig()

        bar = binding.progressBar
        progressDrawable = ProgressDrawable()
        bar.progressDrawable = progressDrawable

        setRemoteConfigValues()

        val onSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                val red = binding.red.progress
                val blue = binding.blue.progress
                val green = binding.green.progress
                val yellow = binding.yellow.progress
                val progress = binding.seekBar.progress

                progressDrawable.setDims(red, green, blue, yellow)
                bar.progress = progress
                progressDrawable.setProgress(progress)
                progressDrawable.callback?.invalidateDrawable(progressDrawable)
                analytic.logEvent("dragged", null)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                analytic.logEvent("dragged", null)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                analytic.logEvent("dragged", null)
            }
        }
        binding.seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener)
        binding.red.setOnSeekBarChangeListener(onSeekBarChangeListener)
        binding.yellow.setOnSeekBarChangeListener(onSeekBarChangeListener)
        binding.blue.setOnSeekBarChangeListener(onSeekBarChangeListener)
        binding.green.setOnSeekBarChangeListener(onSeekBarChangeListener)
    }

    private fun setRemoteConfigValues() {
        progressDrawable.setDims(
            config["red"].asString().toInt(),
            config["green"].asString().toInt(),
            config["blue"].asString().toInt(),
            config["yellow"].asString().toInt()
        )

        binding.apply {
            red.progress = config["red"].asString().toInt()
            blue.progress = config["blue"].asString().toInt()
            yellow.progress = config["yellow"].asString().toInt()
            green.progress = config["green"].asString().toInt()
        }
        progressDrawable.setProgress(config["main"].asString().toInt())
        bar.progress = config["main"].asString().toInt()
    }

}