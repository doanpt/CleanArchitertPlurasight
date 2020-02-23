package com.ddona.banking.local.mapper

import com.ddona.banking.data.model.UserInfoData
import com.ddona.banking.local.model.UserInfoLocal
import javax.inject.Inject

class UserInfoDataLocalMapper @Inject constructor(): Mapper<UserInfoData, UserInfoLocal> {
    override fun from(e: UserInfoLocal): UserInfoData {
        return UserInfoData(
            accountNumber = e.accountNumber,
            displayName = e.displayName,
            address = e.address,
            displayableJoinDate = e.displayableJoinDate,
            premiumCustomer = e.premiumCustomer,
            accountBalance = e.accountBalance,
            accountType = e.accountType,
            unbilledTransactionCount = e.unbilledTransactionCount
        )
    }

    override fun to(t: UserInfoData): UserInfoLocal {
        return UserInfoLocal(
            accountNumber = t.accountNumber,
            displayName = t.displayName,
            address = t.address,
            displayableJoinDate = t.displayableJoinDate,
            premiumCustomer = t.premiumCustomer,
            accountBalance = t.accountBalance,
            accountType = t.accountType,
            unbilledTransactionCount = t.unbilledTransactionCount
        )
    }
}