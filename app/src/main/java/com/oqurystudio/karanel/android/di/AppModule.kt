package com.oqurystudio.karanel.android.di

import android.content.Context
import com.oqurystudio.karanel.android.network.ApiService
import com.oqurystudio.karanel.android.repository.KaranelRepository
import com.oqurystudio.karanel.android.util.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@Module(
    includes = [NetworkModule::class]
)
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideRepository(
        apiService: ApiService
    ) = KaranelRepository(apiService)
}