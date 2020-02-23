package com.ddona.banking.data.mapper

import com.ddona.banking.data.model.UserInfoData
import com.ddona.banking.domain.entites.UserInfoEntity
import javax.inject.Inject

class UserInfoDomainDataMapper @Inject constructor() : Mapper<UserInfoEntity, UserInfoData> {
    override fun from(e: UserInfoData): UserInfoEntity {
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

    override fun to(t: UserInfoEntity): UserInfoData {
        return UserInfoData(
            accountNumber = t.accountNumber,
            displayName = t.displayName,
            address = t.address,
            displayableJoinDate = t.displayableJoinDate,
            premiumCustomer = t.premiumCustomer,
            accountBalance = t.accountBalance,
            accountType = t.accountType,
            unbilledTransactionCount = t.unBilledTransactionCount
        )
    }
}