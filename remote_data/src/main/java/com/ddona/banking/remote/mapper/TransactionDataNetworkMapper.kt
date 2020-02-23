package com.ddona.banking.remote.mapper

import com.ddona.banking.data.model.TransactionData
import com.ddona.banking.remote.model.TransactionNetwork
import javax.inject.Inject

class TransactionDataNetworkMapper @Inject constructor(): Mapper<TransactionData, TransactionNetwork> {
    override fun from(e: TransactionNetwork): TransactionData {
        return TransactionData(
            transactionId = e.transactionId,
            type = e.type,
            amountInCents = e.amountInCents,
            commaSeparatedTags = e.commaSeparatedTags,
            timestamp = e.timestamp,
            flagged = e.flagged,
            remarks = e.remarks
        )
    }

    override fun to(t: TransactionData): TransactionNetwork {
        return TransactionNetwork(
            transactionId = t.transactionId,
            type = t.type,
            amountInCents = t.amountInCents,
            commaSeparatedTags = t.commaSeparatedTags,
            timestamp = t.timestamp,
            flagged = t.flagged,
            remarks = t.remarks
        )
    }
}