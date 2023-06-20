package com.example.bestiario_dq_app.di

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
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
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthApi(): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.123.168:8080/")
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

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
    fun provideMonstruosRepository(
        apiService: ApiService,
        prefs: SharedPreferences
    ): MonstruosRepository {
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