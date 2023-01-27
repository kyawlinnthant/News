package com.kyawlinnthant.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class Io

    @Provides
    @Io
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}