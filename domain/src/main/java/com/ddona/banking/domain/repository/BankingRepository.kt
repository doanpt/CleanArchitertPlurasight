package com.ddona.banking.domain.repository

import com.ddona.banking.domain.entites.TransactionEntity
import com.ddona.banking.domain.entites.UserInfoEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface BankingRepository {
    fun getUserInfo(identifier: String): Observable<UserInfoEntity>

    fun getUserTransactions(userIdentifier: String, limit: Int): Observable<List<TransactionEntity>>

    fun getFilteredTransactions(userIdentifier: String,
                                credit: Boolean,
                                debit: Boolean,
                                flagged: Boolean): Observable<List<TransactionEntity>>

    fun updateTransaction(transaction: TransactionEntity): Completable
}