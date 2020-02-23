package com.ddona.banking.di

import com.ddona.banking.BuildConfig
import com.ddona.banking.presentation.qualifier.UserIdentity
import dagger.Module
import dagger.Provides

@Module
class IdentityModule {

    @Provides
    @UserIdentity
    fun providesUserID(): String = BuildConfig.USER_ID
}