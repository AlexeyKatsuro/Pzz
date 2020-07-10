package com.alexeykatsuro.pzz.base

import com.alexeykatsuro.pzz.BuildConfig
import com.alexeykatsuro.pzz.di.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class PzzApp : DaggerApplication() {


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // Fabric.with(this, Crashlytics()) // disable when we move to Firebase Crashlytics
            // Timber.plant(CrashlyticsTree())
        }

        AndroidThreeTen.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}