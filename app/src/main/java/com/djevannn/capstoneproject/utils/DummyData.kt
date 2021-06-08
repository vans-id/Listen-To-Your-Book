package com.djevannn.capstoneproject.utils

import com.djevannn.capstoneproject.data.source.remote.response.BookResponse

object DummyData {

    fun generateDummyBooks(): List<BookResponse> {
        val books = ArrayList<BookResponse>()
        books.add(
            BookResponse(
                "Nobody To Love",
                "TELYKast",
                "https://itp.live/public/images/2021/03/10/screenshot20210310at61815pm.png",
                "nobody_to_love"
            )
        )
        books.add(
            BookResponse(
                "Follow You",
                "Imagine Dragons",
                "https://i1.sndcdn.com/artworks-0zjlsBvWaZNtCLTX-sKy5QA-t500x500.jpg",
                "follow_you"
            )
        )
        return books
    }

}