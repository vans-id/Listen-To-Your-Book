package com.djevannn.capstoneproject.di

import com.djevannn.capstoneproject.data.BookDataSource
import com.djevannn.capstoneproject.data.BookRepository
import com.djevannn.capstoneproject.data.source.remote.RemoteDataSource
import com.djevannn.capstoneproject.ui.home.HomeViewModel
import com.djevannn.capstoneproject.ui.upload.UploadViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single { RemoteDataSource() }
    single { BookRepository(get()) }
    single<BookDataSource> { get() }
}

val viewModelModule = module {
    viewModel {
        HomeViewModel(
            get()
        )
    }
    viewModel {
        UploadViewModel(
            get()
        )
    }
}