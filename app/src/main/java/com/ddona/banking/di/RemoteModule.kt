package com.ddona.banking.di

import com.ddona.banking.BuildConfig
import com.ddona.banking.data.model.TransactionData
import com.ddona.banking.data.model.UserInfoData
import com.ddona.banking.data.repository.RemoteDataSource
import com.ddona.banking.remote.api.BankingService
import com.ddona.banking.remote.mapper.Mapper
import com.ddona.banking.remote.mapper.TransactionDataNetworkMapper
import com.ddona.banking.remote.mapper.UserInfoDataNetworkMapper
import com.ddona.banking.remote.model.TransactionNetwork
import com.ddona.banking.remote.model.UserInfoNetwork
import com.ddona.banking.remote.source.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [RemoteModule.Binders::class])
class RemoteModule {

    @Module
    interface Binders {

        @Binds
        fun bindsRemoteSource(
            remoteDataSourceImpl: RemoteDataSourceImpl
        ): RemoteDataSource

        @Binds
        fun bindUserInfoMapper(
            userInfoMapper: UserInfoDataNetworkMapper
        ): Mapper<UserInfoData, UserInfoNetwork>

        @Binds
        fun bindTransactionMapper(
            transactionMapper: TransactionDataNetworkMapper
        ): Mapper<TransactionData, TransactionNetwork>
    }

    @Provides
    fun providesBankingService(retrofit: Retrofit): BankingService =
        retrofit.create(BankingService::class.java)


    @Provides
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()


}