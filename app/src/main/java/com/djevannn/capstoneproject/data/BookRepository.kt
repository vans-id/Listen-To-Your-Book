package com.djevannn.capstoneproject.data

import android.net.Uri
import com.djevannn.capstoneproject.data.source.remote.RemoteDataSource
import com.djevannn.capstoneproject.data.source.remote.network.ApiResponse
import com.djevannn.capstoneproject.data.source.remote.response.BookResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class BookRepository(
    private val remoteDataSource: RemoteDataSource,
) : BookDataSource {

    override fun getAllBooks(): Flow<Resource<List<BookResponse>>> =
        flow {
            val apiResponse =
                remoteDataSource.getBookList().first()
            emit(Resource.Loading())
            when (apiResponse) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(apiResponse.data))
                }
                else -> {
                    emit(
                        Resource.Error<List<BookResponse>>(
                            "Something went wrong",
                        )
                    )
                }
            }
        }

    override fun postBook(uri: Uri) = remoteDataSource.postBook(uri)


}