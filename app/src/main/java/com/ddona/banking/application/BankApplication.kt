package com.ddona.banking.application

import com.ddona.banking.di.DaggerBankBuddyAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BankApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerBankBuddyAppComponent.builder().application(this).build()
    }
}