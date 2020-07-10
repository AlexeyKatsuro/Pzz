/*
 * Copyright 2019 LWO LLC
 */

package com.alexeykatsuro.pzz.di

import androidx.lifecycle.ViewModelProvider
import com.alexeykatsuro.pzz.di.PzzViewModuleFactory
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelBuilder {
    @Binds
    internal abstract fun bindViewModelFactory(
        factory: PzzViewModuleFactory
    ): ViewModelProvider.Factory
}
