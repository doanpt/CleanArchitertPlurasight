package com.ddona.banking.local.mapper

import com.ddona.banking.data.model.TransactionData
import com.ddona.banking.local.model.TransactionLocal
import javax.inject.Inject

class TransactionDataLocalMapper @Inject constructor(): Mapper<TransactionData, TransactionLocal> {

    override fun from(e: TransactionLocal): TransactionData {
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

    override fun to(t: TransactionData): TransactionLocal {
        return TransactionLocal(
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