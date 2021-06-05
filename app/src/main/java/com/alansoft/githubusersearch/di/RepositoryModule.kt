package com.alansoft.githubusersearch.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by LEE MIN KYU on 2021/06/05
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
//    @ViewModelScoped
//    @Provides
//    fun provideLocationCacheDataSource() = LocationCacheDataSource()
//
//    @ViewModelScoped
//    @Provides
//    fun provideSearchCacheDataSource() = SearchCacheDataSource()
//
//    @ViewModelScoped
//    @Provides
//    fun provideSearchDataSource(pokeApi: PokeApi) = RemoteDataSource(pokeApi)
//
//    @ViewModelScoped
//    @Provides
//    fun provideSearchRepository(
//        locationCache: LocationCacheDataSource,
//        searchCache: SearchCacheDataSource,
//        remote: RemoteDataSource
//    ) = SearchRepository(locationCache, searchCache, remote)
}