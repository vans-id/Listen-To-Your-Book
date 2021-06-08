package com.djevannn.capstoneproject.data

import android.net.Uri
import com.djevannn.capstoneproject.data.source.remote.response.BookResponse
import kotlinx.coroutines.flow.Flow

interface BookDataSource {

    fun getAllBooks(): Flow<Resource<List<BookResponse>>>
    fun postBook(uri: Uri)

}