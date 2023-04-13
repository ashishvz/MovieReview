package com.ashishvz.moviereview.di

import com.ashishvz.moviereview.viewmodel.MovieViewModel
import org.koin.dsl.module

val viewmodelModule = module {
    single {
        MovieViewModel(get())
    }
}