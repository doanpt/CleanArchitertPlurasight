package com.ddona.banking.domain.usecase

import com.ddona.banking.domain.entites.TransactionEntity
import com.ddona.banking.domain.qualifiers.Background
import com.ddona.banking.domain.qualifiers.Foreground
import com.ddona.banking.domain.repository.BankingRepository
import com.ddona.banking.domain.usecase.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetUserTransactionsTask @Inject constructor(
    private val bankingRepository: BankingRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) : ObservableUseCase<List<TransactionEntity>, GetUserTransactionsTask.Params>(
    backgroundScheduler,
    foregroundScheduler
) {
    override fun generateObservable(input: Params?): Observable<List<TransactionEntity>> {
        if (input == null) {
            throw IllegalArgumentException("GetUserTransactionsTask parameter can't be null")
        }
        return bankingRepository.getUserTransactions(input.identifier, input.limit)
    }

    data class Params(val identifier: String, val limit: Int)
}