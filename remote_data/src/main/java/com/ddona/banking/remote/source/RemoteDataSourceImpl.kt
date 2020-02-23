package com.ddona.banking.remote.source

import com.ddona.banking.data.model.TransactionData
import com.ddona.banking.data.model.UserInfoData
import com.ddona.banking.data.repository.RemoteDataSource
import com.ddona.banking.remote.api.BankingService
import com.ddona.banking.remote.mapper.Mapper
import com.ddona.banking.remote.model.TransactionNetwork
import com.ddona.banking.remote.model.UserInfoNetwork
import io.reactivex.Observable
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val userInfoMapper: Mapper<UserInfoData, UserInfoNetwork>,
    private val transactionMapper: Mapper<TransactionData, TransactionNetwork>,
    private val bankingService: BankingService
) : RemoteDataSource {

    override fun getUserInfo(identifier: String): Observable<UserInfoData> {
        return bankingService.getUserInformation(identifier)
            .map { response ->
                userInfoMapper.from(response.userInfo)
            }
    }

    override fun getUserTransactions(userIdentifier: String, limit: Int):
        Observable<List<TransactionData>> {
        return bankingService.getUserInformation(userIdentifier)
            .map { response ->
                response.transactions.map { transaction: TransactionNetwork ->
                    transactionMapper.from(transaction)
                }
            }
    }
}