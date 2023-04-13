package com.ashishvz.moviereview.di

import com.ashishvz.moviereview.data.repository.MovieRepository
import com.ashishvz.moviereview.data.repository.MovieRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(get())
    }
}