/*
 * Copyright 2022 Mohammed Khalid Hamid.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.khalid.hamid.githubrepos.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.khalid.hamid.githubrepos.di.AppModule.TIME_OUT_INTERVAL
import com.khalid.hamid.githubrepos.network.remote.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DebugModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(TIME_OUT_INTERVAL, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_INTERVAL, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_INTERVAL, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(authInterceptor)
            addInterceptor(chuckerInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun providesChuckerInterceptor(context: Application): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context, // Context on which you are
            showNotification = true, // Boolean for showing Notification, set to true to show and false otherwise
            retentionPeriod = RetentionManager.Period.ONE_WEEK // Period taken to retain the collected data, can be an hour, day or week
        )
        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250000L)
            .alwaysReadResponseBody(true)
            .build()
    }
}
