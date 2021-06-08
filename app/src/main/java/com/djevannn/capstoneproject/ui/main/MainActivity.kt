package com.djevannn.capstoneproject.ui.main

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.djevannn.capstoneproject.R
import com.djevannn.capstoneproject.data.source.remote.response.BookResponse
import com.djevannn.capstoneproject.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private var mMediaPlayer: MediaPlayer? = null
    private lateinit var activityBinding: ActivityMainBinding
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
        val navView: BottomNavigationView = activityBinding.navView

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        activityBinding.btnPlay.setOnClickListener {
            onMainButtonClicked()
        }

    }

    private fun onMainButtonClicked() {
        if (mMediaPlayer == null) {
            Toast.makeText(
                this,
                "Tidak ada buku untuk dibaca",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (!isPlaying) {
            mMediaPlayer?.prepareAsync()
            activityBinding.btnPlay.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_baseline_pause_circle_24,
                    null
                )
            )
        } else {
            if (mMediaPlayer?.isPlaying as Boolean) {
                Toast.makeText(
                    this@MainActivity,
                    "Pause music",
                    Toast.LENGTH_SHORT
                ).show()
                mMediaPlayer?.pause()
                activityBinding.btnPlay.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_baseline_play_circle_24,
                        null
                    )
                )
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Play music",
                    Toast.LENGTH_SHORT
                ).show()
                mMediaPlayer?.start()
                activityBinding.btnPlay.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_baseline_pause_circle_24,
                        null
                    )
                )

            }
        }
    }

    fun initMusic(
        book: BookResponse
    ) {
        mMediaPlayer = MediaPlayer()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val attribute = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            mMediaPlayer?.setAudioAttributes(attribute)
        } else {
            mMediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        }

        // change here later...
        val resourceId = resources.getIdentifier(
            book.url,
            "raw", packageName
        )
        activityBinding.tvBookTitle.text = book.title
        activityBinding.tvBookAuthor.text = book.author
        Glide.with(applicationContext)
            .load(book.image)
            .into(activityBinding.ivPoster)

        val afd =
            applicationContext.resources.openRawResourceFd(resourceId)
        try {
            mMediaPlayer?.setDataSource(
                afd.fileDescriptor,
                afd.startOffset,
                afd.length
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mMediaPlayer?.setOnPreparedListener {
            isPlaying = true
            mMediaPlayer?.start()
        }
        mMediaPlayer?.setOnErrorListener { mp, what, extra -> false }
    }

    fun setMusic(book: BookResponse) {
        val resourceId = resources.getIdentifier(
            book.url,
            "raw", packageName
        )
        activityBinding.tvBookTitle.text = book.title
        activityBinding.tvBookAuthor.text = book.author
        Glide.with(applicationContext)
            .load(book.image)
            .into(activityBinding.ivPoster)

        val afd =
            applicationContext.resources.openRawResourceFd(resourceId)
        try {
            mMediaPlayer?.reset()
            mMediaPlayer?.setDataSource(
                afd.fileDescriptor,
                afd.startOffset,
                afd.length
            )
            mMediaPlayer?.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        isPlaying = true
        activityBinding.btnPlay.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_baseline_pause_circle_24,
                null
            )
        )
    }
}

