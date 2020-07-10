package com.alexeykatsuro.pzz.di

import com.alexeykatsuro.pzz.R
import dagger.Module
import dagger.Provides

@Module
class GlobalHostModule {

    @Provides
    @GlobalHost
    fun provideGlobalHostId(): Int = R.id.main_nav_host

}