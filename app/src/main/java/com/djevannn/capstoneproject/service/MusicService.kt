package com.djevannn.capstoneproject.service

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import java.io.IOException

private const val ACTION_PLAY: String = "com.example.action.PLAY"

class MusicService : Service(), MediaPlayer.OnPreparedListener {

    private var mMediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(
        intent: Intent,
        flags: Int,
        startId: Int
    ): Int {
        when (intent.action as String) {
            ACTION_PLAY -> {
                mMediaPlayer = MediaPlayer() // initialize it here

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val attribute = AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                    mMediaPlayer?.setAudioAttributes(attribute)
                } else {
                    mMediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                }

                val resourceId = resources.getIdentifier(
                    "FILENAME_WITHOUT_EXTENSION",
                    "raw", packageName
                )
                val afd =
                    applicationContext.resources.openRawResourceFd(
                        resourceId
                    )
                try {
                    mMediaPlayer?.setDataSource(
                        afd.fileDescriptor,
                        afd.startOffset,
                        afd.length
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                mMediaPlayer?.apply {
                    setOnPreparedListener(this@MusicService)
                    prepareAsync() // prepare async to not block main thread
                    setOnErrorListener { mp, what, extra -> false }
                }

            }
        }

        return super.onStartCommand(intent, flags, startId)
    }



    override fun onPrepared(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()
    }

}