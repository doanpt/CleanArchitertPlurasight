package com.ddona.banking.domain.entites

data class TransactionEntity(
    val transactionId: String,
    val type: String,
    val amountInCents: Long,
    val commaSeparatedTags: String,
    val timestamp: Long,
    val flagged: Boolean,
    val remarks: String
) {
    companion object {
        private const val DEBIT_HVT_LIMIT = (4000 * 100).toLong()
        private const val CREATE_HVT_LIMIT = (4000 * 100).toLong()

    }
    val isHVT : Boolean
    get() = when(type) {
        "credit" -> amountInCents >= CREATE_HVT_LIMIT
        "debit" -> amountInCents >= DEBIT_HVT_LIMIT
        else -> false
    }
}