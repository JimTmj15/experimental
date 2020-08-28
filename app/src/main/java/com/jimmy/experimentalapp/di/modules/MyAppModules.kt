package com.jimmy.experimentalapp.di.modules

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jimmy.experimentalapp.BuildConfig
import com.jimmy.experimentalapp.di.qualifiers.BaseUrl
import com.jimmy.experimentalapp.repository.api.ApiHelper
import com.jimmy.experimentalapp.repository.api.ApiHelperImpl
import com.jimmy.experimentalapp.repository.api.ApiService
import com.jimmy.experimentalapp.repository.database.AppDatabase
import com.jimmy.experimentalapp.repository.database.DatabaseHelper
import com.jimmy.experimentalapp.repository.database.DatabaseHelperImpl
import com.jimmy.experimentalapp.repository.database.WeatherDao
import com.jimmy.experimentalapp.utils.network.Interceptor
import com.jimmy.experimentalapp.utils.network.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class MyAppModules {


    @BaseUrl
    val baseUrl = "https://samples.openweathermap.org"

    @Provides
    @Singleton
    fun httpClient(interceptor: Interceptor): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun retrofitInstance(httpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun apiHelperInstance(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun apiServiceInstance(apiHelperImpl: ApiHelperImpl): ApiHelper = apiHelperImpl

    @Provides
    @Singleton
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ):AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "AppDatabase")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun daoInstance(helperImpl: DatabaseHelperImpl): DatabaseHelper = helperImpl

    @Provides
    @Singleton
    fun helperInstance(database: AppDatabase): WeatherDao = database.weatherDao()
}