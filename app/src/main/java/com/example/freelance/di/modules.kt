package com.example.freelance.di

import com.example.freelance.data.impl.HomeRepo
import com.example.freelance.data.impl.HomeRepoImpl
import com.example.freelance.ui.activity.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val models = module {
    viewModel {
        MainViewModel()
    }
}

val impls = module {

    single<HomeRepo> {
        HomeRepoImpl(
            get()
        )
    }
}

val utilities = module {
    single<ResourceProvider> { AndroidResourceProvider(get()) }
}

val listModules = listOf(utilities, networks, models, impls)
