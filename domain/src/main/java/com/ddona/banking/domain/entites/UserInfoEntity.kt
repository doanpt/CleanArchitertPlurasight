package com.ddona.banking.domain.entites

data class UserInfoEntity(
    val accountNumber: String,
    val displayName: String,
    val address: String,
    val displayableJoinDate: String,
    val premiumCustomer: Boolean,
    val accountBalance: Double,
    val accountType: String,
    val unBilledTransactionCount: Int
) {
    companion object {
        private const val ACCOUNT_UPGRADE_BALACNE = 30000
    }

    val isEligibleForUpgrade: Boolean
        get() = accountBalance >= ACCOUNT_UPGRADE_BALACNE
}