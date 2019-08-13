package com.uipath.seamlessboard.injection

import androidx.room.Room
import com.uipath.seamlessboard.database.BoardDatabase
import com.uipath.seamlessboard.presentation.addreview.AddReviewViewModel
import com.uipath.seamlessboard.presentation.board.BoardViewModel
import com.uipath.seamlessboard.presentation.login.LoginViewModel
import com.uipath.seamlessboard.presentation.restaurant.RestaurantViewModel
import com.uipath.seamlessboard.repository.RestaurantRepository
import com.uipath.seamlessboard.repository.RestaurantRepositoryImpl
import com.uipath.seamlessboard.repository.UserRepository
import com.uipath.seamlessboard.repository.UserRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module(override = true) {
    viewModel { AddReviewViewModel(get()) }

    viewModel { BoardViewModel(get(), get()) }

    viewModel { RestaurantViewModel(get()) }

    viewModel { LoginViewModel(get()) }
}

val repoModule = module(override = true) {
    single<RestaurantRepository> { RestaurantRepositoryImpl(get(), get()) }

    single<UserRepository> { UserRepositoryImpl() }
}

val roomModule = module {
    single { get<BoardDatabase>().restaurantDao() }

    single {
        Room.databaseBuilder(androidApplication(), BoardDatabase::class.java, "Restaurant_database").build()
    }
}

val roomTestModule = module(override = true) {
    single { get<BoardDatabase>().restaurantDao() }

    single {
        Room.inMemoryDatabaseBuilder(androidApplication(), BoardDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}