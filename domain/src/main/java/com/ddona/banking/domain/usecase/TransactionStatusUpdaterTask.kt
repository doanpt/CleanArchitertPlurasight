package com.ddona.banking.domain.usecase

import com.ddona.banking.domain.entites.TransactionEntity
import com.ddona.banking.domain.qualifiers.Background
import com.ddona.banking.domain.qualifiers.Foreground
import com.ddona.banking.domain.repository.BankingRepository
import com.ddona.banking.domain.usecase.base.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class TransactionStatusUpdaterTask @Inject constructor(
    private val bankingRepository: BankingRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) : CompletableUseCase<TransactionEntity>(
    backgroundScheduler,
    foregroundScheduler
) {

    override fun generateCompletable(input: TransactionEntity?): Completable {
        if (input == null) {
            throw IllegalArgumentException("TransactionStatusUpdaterTask parameter can't be null")
        }
        return bankingRepository.updateTransaction(input)
    }
}