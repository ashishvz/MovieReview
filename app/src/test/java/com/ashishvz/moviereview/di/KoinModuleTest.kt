package com.ashishvz.moviereview.di

import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.check.checkKoinModules

class KoinModuleTest: KoinTest {

    @Test
    fun checkModules() {
        checkKoinModules(listOf(networkModule, dataSourceModule, pagingModule, repositoryModule, viewmodelModule))
    }
}