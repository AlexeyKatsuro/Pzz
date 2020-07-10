package com.alexeykatsuro.pzz.di.network

import com.alexeykatsuro.pzz.data.network.BasketService
import com.alexeykatsuro.pzz.data.network.PizzaService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
class ServicesModule {
    @Singleton
    @Provides
    fun providePizzaService(
        retrofit: Retrofit
    ): PizzaService = retrofit.create()

    @Singleton
    @Provides
    fun provideBasketService(
        retrofit: Retrofit
    ): BasketService = retrofit.create()
}