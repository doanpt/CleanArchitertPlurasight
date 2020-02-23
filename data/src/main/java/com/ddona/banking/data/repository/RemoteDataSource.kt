package com.ddona.banking.data.repository

import com.ddona.banking.data.model.TransactionData
import com.ddona.banking.data.model.UserInfoData
import io.reactivex.Observable

interface RemoteDataSource {

    fun getUserInfo(identifier: String): Observable<UserInfoData>

    fun getUserTransactions(userIdentifier: String, limit: Int): Observable<List<TransactionData>>
}