package com.ddona.banking.di

import android.app.Application
import android.content.Context
import com.ddona.banking.ui.home.HomeActivity
import com.ddona.banking.ui.transactions.TransactionListActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @ContributesAndroidInjector
    internal abstract fun contributesMainActivity(): HomeActivity

    @ContributesAndroidInjector
    internal abstract fun contributesTransactionList(): TransactionListActivity

}