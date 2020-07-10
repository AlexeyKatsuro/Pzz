package com.alexeykatsuro.pzz.base

import android.content.Context
import androidx.annotation.LayoutRes
import com.airbnb.mvrx.BaseMvRxFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class DaggerMvRxFragment(@LayoutRes contentLayoutId: Int = 0) :
    BaseMvRxFragment(contentLayoutId), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}
