package com.alexeykatsuro.pzz.di.network

import com.alexeykatsuro.pzz.BuildConfig
import com.alexeykatsuro.pzz.data.network.AcceptInterceptor
import com.alexeykatsuro.pzz.data.network.SessionCookiesInterceptor
import com.alexeykatsuro.pzz.utils.Timeouts
import com.alexeykatsuro.pzz.utils.extensions.setupTimeout
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module(
    includes = [
        ClientModule::class,
        ServicesModule::class]
)
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    @Singleton
    @Provides
    fun provideAcceptInterceptor(): AcceptInterceptor {
        return AcceptInterceptor()
    }


    @Singleton
    @Provides
    fun provideMoshiConverter(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

}

@Module
class ClientModule {

    @Singleton
    @Provides
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        acceptInterceptor: AcceptInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(SessionCookiesInterceptor()).apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(httpLoggingInterceptor)
            }
        }.setupTimeout(Timeouts.Default).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        moshiConverter: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("${BuildConfig.API_URL}/")
            .addConverterFactory(moshiConverter)
            .client(httpClient)
            .build()
    }
}