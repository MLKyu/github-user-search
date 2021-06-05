package com.alansoft.githubusersearch.di

import com.alansoft.githubusersearch.data.SearchDataSource
import com.alansoft.githubusersearch.data.api.SearchApi
import com.alansoft.githubusersearch.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by LEE MIN KYU on 2021/06/05
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @ViewModelScoped
    @Provides
    fun provideSearchDataSource(searchApi: SearchApi) = SearchDataSource(searchApi)

    @ViewModelScoped
    @Provides
    fun provideSearchRepository(remote: SearchDataSource) = MainRepository(remote)
}