
package com.example.jungleconsultingtesttask.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.jungleconsultingtesttask.data.api.UserApi
import com.example.jungleconsultingtesttask.data.Repository
import com.example.jungleconsultingtesttask.data.local.UserDatabase
import com.example.jungleconsultingtesttask.data.local.user.UserDbEntity
import com.example.jungleconsultingtesttask.data.api.user.UserMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun getOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


    @Provides
    @Singleton
    fun provideUserApi(client: OkHttpClient): UserApi {
        return Retrofit.Builder()
            .baseUrl(UserApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideUserPager(userDb: UserDatabase, userApi: UserApi): Pager<Int, UserDbEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = UserMediator(
                db = userDb,
                api = userApi
            ),
            pagingSourceFactory = {
                userDb.userDao.getUsers()
            }
        )
    }

    @Provides
    fun provideUserRepository(userApi: UserApi, userDb: UserDatabase): Repository {
        return Repository(api = userApi, db = userDb)
    }
}