package com.gmshascreations.beautifulquran

import android.animation.ObjectAnimator
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.skyfishjy.library.RippleBackground
import com.yangp.ypwaveview.YPWaveView
import java.util.*
import kotlin.random.Random

class PlayActvitiy : AppCompatActivity() {
    lateinit var mediaPlayer: MediaPlayer
    lateinit var timer: Timer
    lateinit var progress: YPWaveView
    lateinit var useHeadphones: TextView

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

        useHeadphones = findViewById(R.id.useHeaphones)
        val  animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val  animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        useHeadphones.visibility =View.VISIBLE

        useHeadphones.startAnimation(animationFadeIn)
        Handler().postDelayed({
           useHeadphones.startAnimation(animationFadeOut)
            useHeadphones.visibility =View.GONE
        }, 3000)

        progress = findViewById(R.id.progress)
        progress.max = mediaPlayer.duration
        timer = Timer()

        timer.schedule(object : TimerTask() {
            override fun run() {

                progress.progress = mediaPlayer.currentPosition
            }
        }, 0, 1000)
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        timer.cancel()
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer.isPlaying)
            mediaPlayer.pause()
        timer.cancel()
    }

    override fun onResume() {
        super.onResume()
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            timer.schedule(object : TimerTask() {
                override fun run() {

                    progress.progress = mediaPlayer.currentPosition
                }
            }, 0, 1000)
        }
    }
}