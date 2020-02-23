package com.ddona.banking.di

import android.app.Application
import com.ddona.banking.application.BankApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        DomainModule::class,
        DataModule::class,
        LocalPersistenceModule::class,
        RemoteModule::class,
        IdentityModule::class,
        PresentationModule::class,
        AppModule::class
    ]
)
interface BankBuddyAppComponent : AndroidInjector<BankApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): BankBuddyAppComponent
    }

    override fun inject(app: BankApplication)
}