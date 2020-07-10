/*
 * Copyright 2019 LWO LLC
 */

package com.alexeykatsuro.pzz.di

import com.alexeykatsuro.pzz.base.PzzApp
import com.alexeykatsuro.pzz.di.db.DatabaseModule
import com.alexeykatsuro.pzz.di.network.NetworkModule
import com.alexeykatsuro.pzz.ui.MainBuilder
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        GlobalHostModule::class,
        AndroidSupportInjectionModule::class,
        MainBuilder::class
    ]
)
interface AppComponent : AndroidInjector<PzzApp> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<PzzApp>
}
