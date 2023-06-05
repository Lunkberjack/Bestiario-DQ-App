package com.example.bestiario_dq_app.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.bestiario_dq_app.data.remote.AuthApi
import com.example.bestiario_dq_app.data.repositories.AuthRepositoryImpl
import com.example.bestiario_dq_app.domain.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            // La IP actual del dispositivo donde corre la API.
            .baseUrl("http://192.168.226.229:8080/")
            // .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, prefs: SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(api, prefs)
    }
}
