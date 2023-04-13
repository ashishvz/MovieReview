package com.ashishvz.moviereview.di

import com.ashishvz.moviereview.data.remote.RemoteDataSource
import com.ashishvz.moviereview.data.remote.RemoteDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    single<RemoteDataSource> {
        RemoteDataSourceImpl(get())
    }
}