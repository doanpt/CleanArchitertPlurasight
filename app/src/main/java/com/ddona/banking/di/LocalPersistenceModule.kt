package com.ddona.banking.di

import android.app.Application
import com.ddona.banking.data.model.TransactionData
import com.ddona.banking.data.model.UserInfoData
import com.ddona.banking.data.repository.LocalDataSource
import com.ddona.banking.local.database.BankBuddyDatabase
import com.ddona.banking.local.mapper.Mapper
import com.ddona.banking.local.mapper.TransactionDataLocalMapper
import com.ddona.banking.local.mapper.UserInfoDataLocalMapper
import com.ddona.banking.local.model.TransactionLocal
import com.ddona.banking.local.model.UserInfoLocal
import com.ddona.banking.local.source.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module(includes = [LocalPersistenceModule.Binders::class])
class LocalPersistenceModule {

    @Module
    interface Binders {

        @Binds
        fun bindsLocalDataSource(
            localDataSourceImpl: LocalDataSourceImpl
        ): LocalDataSource

        @Binds
        fun bindUserInfoMapper(
            userInfoMapper: UserInfoDataLocalMapper
        ): Mapper<UserInfoData, UserInfoLocal>

        @Binds
        fun bindTransactionMapper(
            transactionMapper: TransactionDataLocalMapper
        ): Mapper<TransactionData, TransactionLocal>
    }

    @Provides
    @Singleton
    fun providesDatabase(
        application: Application
    ) = BankBuddyDatabase.getInstance(application.applicationContext)

    @Provides
    @Singleton
    fun providesUserInfoDAO(
        bankBuddyDB: BankBuddyDatabase
    ) = bankBuddyDB.getUserInfoDao()

    @Provides
    @Singleton
    fun providesTransactionDAO(
        bankBuddyDB: BankBuddyDatabase
    ) = bankBuddyDB.getTransactionDao()

}
