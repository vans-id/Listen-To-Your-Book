package com.djevannn.capstoneproject.data.source.remote

import android.net.Uri
import android.util.Log
import com.djevannn.capstoneproject.data.source.remote.network.ApiResponse
import com.djevannn.capstoneproject.data.source.remote.response.BookResponse
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RemoteDataSource {

    suspend fun getBookList(): Flow<ApiResponse<List<BookResponse>>> {
        return callbackFlow {
            val db = Firebase.firestore
            val listener = db.collection("users")
                .addSnapshotListener { snapshot, exception ->
                    if (snapshot != null) {
                        Log.d(
                            "Firebase",
                            snapshot.documents.toString()
                        )
                        if (!snapshot.isEmpty) {
                            offer(
                                ApiResponse.Success(
                                    snapshot.toObjects(
                                        BookResponse::class.java
                                    )
                                )
                            )
                        }
                    }

                    // If exception occurs, cancel this scope with exception message.
                    exception?.let {
                        offer(ApiResponse.Error(it.message.toString()))
                        cancel(it.message.toString())
                    }
                }

            awaitClose {
                listener.remove()
                cancel()
            }
        }
    }

    fun postBook(uri: Uri) {
        val name = uri.lastPathSegment!!
        val mStorage: StorageReference =
            FirebaseStorage.getInstance().getReference("Uploads")
        val mReference = mStorage.child(name)
        mReference.putFile(uri)
    }
}