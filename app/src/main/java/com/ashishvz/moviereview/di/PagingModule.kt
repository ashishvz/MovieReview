package com.ashishvz.moviereview.di

import com.ashishvz.moviereview.data.paging.MoviePagingSource
import org.koin.dsl.module

val pagingModule = module {
    single {
        MoviePagingSource(get())
    }
}