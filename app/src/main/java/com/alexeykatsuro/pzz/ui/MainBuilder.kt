/*
 * Copyright 2019 LWO LLC
 */

package com.alexeykatsuro.pzz.ui

import android.content.Context
import com.alexeykatsuro.pzz.di.ViewModelBuilder
import com.alexeykatsuro.pzz.MainActivity
import com.alexeykatsuro.pzz.di.ActivityScoped
import com.alexeykatsuro.pzz.di.PerActivity
import com.alexeykatsuro.pzz.ui.basket.BasketBuilder
import com.alexeykatsuro.pzz.ui.home.HomeBuilder
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainBuilder {
    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            AssistedBuilder::class,
            MainModuleBinds::class,
            ViewModelBuilder::class,
            HomeBuilder::class,
            BasketBuilder::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity


}

@Module(includes = [AssistedInject_AssistedBuilder::class])
@AssistedModule
interface AssistedBuilder

@Module
abstract class MainModuleBinds {
    @Binds
    @PerActivity
    abstract fun bindContext(activity: MainActivity): Context
}