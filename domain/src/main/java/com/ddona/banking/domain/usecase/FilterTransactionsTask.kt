package com.ddona.banking.domain.usecase

import com.ddona.banking.domain.entites.TransactionEntity
import com.ddona.banking.domain.qualifiers.Background
import com.ddona.banking.domain.qualifiers.Foreground
import com.ddona.banking.domain.repository.BankingRepository
import com.ddona.banking.domain.usecase.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class FilterTransactionsTask @Inject constructor(
    private val bankingRepository: BankingRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) : ObservableUseCase<List<TransactionEntity>, FilterTransactionsTask.Params>(
    backgroundScheduler,
    foregroundScheduler
) {
    override fun generateObservable(input: Params?): Observable<List<TransactionEntity>> {
        if (input == null) {
            throw IllegalArgumentException("FilterTransactionsTask parameter can't be null")
        }
        return bankingRepository.getFilteredTransactions(
            userIdentifier = input.userIdentifier,
            credit = input.credit,
            debit = input.debit,
            flagged = input.flagged
        )
    }

    data class Params(
        val userIdentifier: String,
        val credit: Boolean,
        val debit: Boolean,
        val flagged: Boolean
    )

}