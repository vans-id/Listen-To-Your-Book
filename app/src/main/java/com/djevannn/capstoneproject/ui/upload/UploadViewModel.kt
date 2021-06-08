package com.djevannn.capstoneproject.ui.upload

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.djevannn.capstoneproject.data.BookRepository

class UploadViewModel(private val bookRepository: BookRepository) :
    ViewModel() {

    fun upload(uri: Uri) = bookRepository.postBook(uri)
}