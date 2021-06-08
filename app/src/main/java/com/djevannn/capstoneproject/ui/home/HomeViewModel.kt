package com.djevannn.capstoneproject.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.djevannn.capstoneproject.data.BookRepository

class HomeViewModel(bookRepository: BookRepository) :
    ViewModel() {

    val books = bookRepository.getAllBooks().asLiveData()

}