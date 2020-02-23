package com.ddona.banking.di

import com.ddona.banking.data.mapper.Mapper
import com.ddona.banking.data.mapper.TransactionDomainDataMapper
import com.ddona.banking.data.mapper.UserInfoDomainDataMapper
import com.ddona.banking.data.model.TransactionData
import com.ddona.banking.data.model.UserInfoData
import com.ddona.banking.data.repository.BankingRepositoryImpl
import com.ddona.banking.domain.entites.TransactionEntity
import com.ddona.banking.domain.entites.UserInfoEntity
import com.ddona.banking.domain.repository.BankingRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindsRepository(
        repoImpl: BankingRepositoryImpl
    ): BankingRepository

    @Binds
    abstract fun bindsUserMapper(
        mapper: UserInfoDomainDataMapper
    ): Mapper<UserInfoEntity, UserInfoData>

    @Binds
    abstract fun bindsTransactionMapper(
        mapper: TransactionDomainDataMapper
    ): Mapper<TransactionEntity, TransactionData>
}