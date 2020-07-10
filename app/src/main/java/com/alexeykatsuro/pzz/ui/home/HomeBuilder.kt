package com.alexeykatsuro.pzz.ui.home

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.alexeykatsuro.pzz.di.FragmentScoped

@Module
internal abstract class HomeBuilder {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): HomeFragment

    @Binds
    internal abstract fun bindHomeNavigation(impl: HomeNavigationImpl): HomeNavigation
}