package com.ddona.banking.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ddona.banking.domain.entites.TransactionEntity
import com.ddona.banking.domain.entites.UserInfoEntity
import com.ddona.banking.presentation.factory.ViewModelFactory
import com.ddona.banking.presentation.mapper.Mapper
import com.ddona.banking.presentation.mapper.TransactionEntityMapper
import com.ddona.banking.presentation.mapper.UserInfoEntityMapper
import com.ddona.banking.presentation.model.Transaction
import com.ddona.banking.presentation.model.UserInfo
import com.ddona.banking.presentation.viewmodels.HomeViewModel
import com.ddona.banking.presentation.viewmodels.TransactionsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class PresentationModule {

    @Binds
    abstract fun bindsViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindsHomeViewModel(homeVM: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionsViewModel::class)
    abstract fun bindsTransactionsViewModel(transactionsVM: TransactionsViewModel): ViewModel

    @Binds
    abstract fun bindsUserInfoMapper(
        userInfoEntityMapper: UserInfoEntityMapper
    ): Mapper<UserInfoEntity, UserInfo>

    @Binds
    abstract fun bindsTransactionMapper(
        transactionEntityMapper: TransactionEntityMapper
    ): Mapper<TransactionEntity, Transaction>
}