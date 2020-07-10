package com.alexeykatsuro.pzz.di

import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.coroutineScope
import com.alexeykatsuro.pzz.base.PzzApp
import com.alexeykatsuro.pzz.data.*
import com.alexeykatsuro.pzz.utils.MainThreadExecutor
import dagger.Module
import dagger.Provides
import com.alexeykatsuro.pzz.utils.extensions.deviceId
import com.alexeykatsuro.pzz.utils.extensions.hasBiometrics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.util.*
import java.util.concurrent.Executor
import javax.inject.Singleton

@Module()
class AppModule {
    @Provides
    fun provideContext(application: PzzApp): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideCoroutineDispatchers() = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )

    @Provides
    @ProcessLifetime
    fun provideLongLifetimeScope(): CoroutineScope {
        return ProcessLifecycleOwner.get().lifecycle.coroutineScope
    }

    @Provides
    @UiExecutor
    fun provideMainThreadExecutor(): Executor {
        return MainThreadExecutor()
    }

    @Provides
    @AppInstanceId
    fun provideAppInstanceId(context: Context): String {
        return context.deviceId // TODO Need to use Firebase Instance ID.
    }

    @Provides
    @HasBiometrics
    fun provideHasBiometrics(context: Context): Boolean {
        return context.hasBiometrics()
    }

    @Singleton
    @Provides
    @AppOs
    fun provideAppOs(): String {
        return "ANDROID"
    }



    @Provides
    @AppLang
    fun provideAppLang(): String {
        return Locale.getDefault().language
    }

    @Singleton
    @Provides
    @CacheDir
    fun provideCacheDir(application: PzzApp): File = application.cacheDir
}