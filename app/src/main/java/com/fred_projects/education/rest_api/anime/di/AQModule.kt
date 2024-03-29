package com.fred_projects.education.rest_api.anime.di

import com.fred_projects.database.MainDB
import com.fred_projects.education.rest_api.anime.model.repository.*
import com.fred_projects.education.rest_api.anime.model.service.IAQService
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AQModule {
    @Provides
    @Singleton
    fun provideAQService(): IAQService = Retrofit.Builder().baseUrl(IAQService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(IAQService::class.java)
    @Provides
    @Singleton
    fun provideAQRepository(api: IAQService, db: MainDB): IAQRepository = AQRepository(api, db.aqDao)
}