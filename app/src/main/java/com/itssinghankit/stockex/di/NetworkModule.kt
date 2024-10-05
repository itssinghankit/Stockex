package com.itssinghankit.stockex.di

import com.itssinghankit.stockex.BuildConfig
import com.itssinghankit.stockex.data.remote.AlphaVantageApi
import com.itssinghankit.stockex.data.remote.StockexApi
import com.itssinghankit.stockex.data.remote.StockexInterceptor
import com.itssinghankit.stockex.data.repository.RepositoryImplementation
import com.itssinghankit.stockex.domain.repository.RepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Rapid

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AlphaVantage

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: StockexInterceptor,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Rapid
    fun providesRetrofitRapid(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

    @Singleton
    @Provides
    @AlphaVantage
    fun providesAlphaVantageRetrofitRapid(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.ALPHA_VANTAGE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }
    @Singleton
    @Provides
    fun providesStockexApiInterface(@Rapid retrofit: Retrofit): StockexApi {
        return retrofit.create(StockexApi::class.java)
    }

    @Singleton
    @Provides
    fun providesAlphaApiInterface(@AlphaVantage retrofit: Retrofit): AlphaVantageApi {
        return retrofit.create(AlphaVantageApi::class.java)
    }

    @Singleton
    @Provides
    fun providesAuthRepository(stockexApi: StockexApi,alphaVantageApi: AlphaVantageApi): RepositoryInterface {
        return RepositoryImplementation(stockexApi,alphaVantageApi)
    }

}