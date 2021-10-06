package com.oqurystudio.karanel.android.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.oqurystudio.karanel.android.BuildConfig
import com.oqurystudio.karanel.android.network.ApiService
import com.oqurystudio.karanel.android.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    internal fun provideBaseUrl(): String {
        // TODO Update
        return "https://api.github.com"
    }

    @Provides
    @Singleton
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(loggingInterceptor)
        builder.connectTimeout(Constant.TIME_OUT, TimeUnit.SECONDS)
        builder.writeTimeout(Constant.TIME_OUT, TimeUnit.SECONDS)
        builder.readTimeout(Constant.TIME_OUT, TimeUnit.SECONDS)
        return builder.build()
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideRetrofitService(baseUrl: String, okHttpClient: OkHttpClient): ApiService {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(ApiService::class.java)
    }
}