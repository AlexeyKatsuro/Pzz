package com.alexeykatsuro.pzz.ui.basket

import com.alexeykatsuro.pzz.di.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class BasketBuilder {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeBasketFragment(): BasketFragment

    @Binds
    internal abstract fun bindBasketNavigation(impl: BasketNavigationImpl): BasketNavigation
}