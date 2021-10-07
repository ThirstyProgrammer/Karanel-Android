package com.oqurystudio.karanel.android.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [NetworkModule::class]
)
@InstallIn(SingletonComponent::class)
class AppModule {

}