package com.kyawlinnthant.news.di

import com.kyawlinnthant.news.data.repo.NewsRepositoryImpl
import com.kyawlinnthant.news.domain.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindsRepository(repo: NewsRepositoryImpl): NewsRepository

}