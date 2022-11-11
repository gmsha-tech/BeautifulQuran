package com.gmshascreations.beautifulquran

import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.skyfishjy.library.RippleBackground
import java.util.*
import kotlin.random.Random

class PlayActvitiy : AppCompatActivity() {
    lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // remove title
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val WMLP = window.attributes
        WMLP.screenBrightness = 0.15f
        window.attributes = WMLP
        setContentView(R.layout.activity_play_actvitiy)

        val rippleBackground: RippleBackground = findViewById(R.id.content)
        rippleBackground.startRippleAnimation()

        mediaPlayer = MediaPlayer.create(this, R.raw.quran_audio)
        mediaPlayer.start() // no need to call prepare(); create() does that for you

    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer.isPlaying)
            mediaPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    private fun createWaveform(): IntArray? {
        val random = Random(System.currentTimeMillis())
        val length = 50
        val values = IntArray(length)
        var maxValue = 0
        for (i in 0 until length) {
            val newValue: Int = 5 + random.nextInt(50)
            if (newValue > maxValue) {
                maxValue = newValue
            }
            values[i] = newValue
        }
        return values
    }

}