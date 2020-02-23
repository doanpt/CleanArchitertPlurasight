package com.ddona.banking.presentation.mapper

import com.ddona.banking.domain.entites.UserInfoEntity
import com.ddona.banking.presentation.model.UserInfo
import javax.inject.Inject

class UserInfoEntityMapper @Inject constructor() : Mapper<UserInfoEntity, UserInfo> {
    override fun from(e: UserInfo): UserInfoEntity {
        return UserInfoEntity(
            accountNumber = e.accountNumber,
            displayName = e.displayName,
            address = e.address,
            displayableJoinDate = e.displayableJoinDate,
            premiumCustomer = e.premiumCustomer,
            accountBalance = e.accountBalance,
            accountType = e.accountType,
            unBilledTransactionCount = e.unbilledTransactionCount
        )
    }

    override fun to(t: UserInfoEntity): UserInfo {
        return UserInfo(
            accountNumber = t.accountNumber,
            displayName = t.displayName,
            address = t.address,
            displayableJoinDate = t.displayableJoinDate,
            premiumCustomer = t.premiumCustomer,
            accountBalance = t.accountBalance,
            accountType = t.accountType,
            unbilledTransactionCount = t.unBilledTransactionCount,
            isEligibleForUpgrade = t.isEligibleForUpgrade
        )
    }
}