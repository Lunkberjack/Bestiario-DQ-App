package com.example.bestiario_dq_app.di

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.example.bestiario_dq_app.data.local.MonstruoDao
import com.example.bestiario_dq_app.data.local.MonstruoDatabase
import com.example.bestiario_dq_app.data.remote.ApiService
import com.example.bestiario_dq_app.data.repositories.AuthRepositoryImpl
import com.example.bestiario_dq_app.data.repositories.MonstruosRepositoryImpl
import com.example.bestiario_dq_app.domain.repositories.AuthRepository
import com.example.bestiario_dq_app.domain.repositories.MonstruosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
            //.baseUrl("http://192.168.178.114:8080/")
            //.baseUrl("http://192.168.123.229:8080/")
            .baseUrl("http://192.168.123.168:8080/")
            .build()
            .create(ApiService::class.java)
    }

    /*@Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("prefs", MODE_PRIVATE)
    }

     */

    // Inyectar las defaultSharedPreferences asegura que las podamos recuperar en toda la app sólo
    // aportando el mismo contexto.
    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext appContext: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(appContext)
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

    @Provides
    @Singleton
    fun provideMonstruoDatabase(application: Application): MonstruoDatabase {
        return Room.databaseBuilder(
            application,
            MonstruoDatabase::class.java,
            "monstruo_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMonstruoDao(database: MonstruoDatabase): MonstruoDao {
        return database.dao
    }
}
