package com.ddona.banking.presentation.model

data class Transaction(
    val transactionId: String,
    val type: String,
    val amountInCents: Long,
    val commaSeparatedTags: String,
    val timestamp: Long,
    val flagged: Boolean,
    val remarks: String,
    val isHVT: Boolean
)