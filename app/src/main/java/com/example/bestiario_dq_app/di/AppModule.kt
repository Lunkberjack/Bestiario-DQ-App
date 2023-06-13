package com.example.bestiario_dq_app.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.bestiario_dq_app.data.remote.ApiService
import com.example.bestiario_dq_app.data.repositories.AuthRepositoryImpl
import com.example.bestiario_dq_app.data.repositories.MonstruosRepositoryImpl
import com.example.bestiario_dq_app.domain.repositories.AuthRepository
import com.example.bestiario_dq_app.domain.repositories.MonstruosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthApi(): ApiService {
        return Retrofit.Builder()
            // La IP actual del dispositivo donde corre la API.
            .addConverterFactory(GsonConverterFactory.create())
            //.baseUrl("http://10.42.2.199:8080/")
            .baseUrl("http://192.168.178.114:8080/")
            //.baseUrl("http://192.168.226.229:8080/")
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService, prefs: SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(apiService, prefs)
    }

    @Provides
    @Singleton
    fun provideMonstruosRepository(apiService: ApiService, prefs: SharedPreferences): MonstruosRepository {
        return MonstruosRepositoryImpl(apiService, prefs)
    }
}
