package com.example.myhome.di

import com.example.myhome.data.datasources.dao.Dao
import com.example.myhome.data.datasources.dao.DaoImpl
import com.example.myhome.data.datasources.service.ApiService
import com.example.myhome.data.datasources.service.ApiServiceImpl
import com.example.myhome.data.models.db.CameraRealm
import com.example.myhome.data.models.db.DoorRealm
import com.example.myhome.data.repositories.MyHouseRepositoryImpl
import com.example.myhome.domain.repositories.MyHouseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.serialization.json.Json
import javax.inject.Singleton

const val BASE_URL = "http://cars.cprogroup.ru/api/rubetek"
private const val realmVersion = 1L

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                url(BASE_URL)
            }
            install(ContentNegotiation) {
                json(Json)
            }
        }
    }

    @Singleton
    @Provides
    fun provideApiService(httpClient: HttpClient): ApiService = ApiServiceImpl(httpClient)

    @Singleton
    @Provides
    fun provideDao(
        realmConfig: RealmConfiguration
    ): Dao = DaoImpl(Realm.open(realmConfig))

    @Singleton
    @Provides
    fun providesRealmConfig(): RealmConfiguration =
        RealmConfiguration
            .Builder(setOf(CameraRealm::class, DoorRealm::class))
            .schemaVersion(realmVersion)
            .build()

    @Singleton
    @Provides
    fun provideRepo(
        service: ApiService,
        dao: Dao
    ): MyHouseRepository = MyHouseRepositoryImpl(service, dao)
}