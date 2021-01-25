package com.example.customprogressbar

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.customprogressbar.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var bar: ProgressBar
    private lateinit var progressDrawable: ProgressDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bar = binding.progressBar
        progressDrawable = ProgressDrawable()
        bar.progressDrawable = progressDrawable

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
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener)
        binding.red.setOnSeekBarChangeListener(onSeekBarChangeListener)
        binding.yellow.setOnSeekBarChangeListener(onSeekBarChangeListener)
        binding.blue.setOnSeekBarChangeListener(onSeekBarChangeListener)
        binding.green.setOnSeekBarChangeListener(onSeekBarChangeListener)

    }

}