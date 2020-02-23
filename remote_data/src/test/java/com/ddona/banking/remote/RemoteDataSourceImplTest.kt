package com.ddona.banking.remote

import com.ddona.banking.data.repository.RemoteDataSource
import com.ddona.banking.remote.api.BankingService
import com.ddona.banking.remote.mapper.TransactionDataNetworkMapper
import com.ddona.banking.remote.mapper.UserInfoDataNetworkMapper
import com.ddona.banking.remote.model.ResponseWrapper
import com.ddona.banking.remote.source.RemoteDataSourceImpl
import com.ddona.banking.remote.utils.TestDataGenerator
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class RemoteDataSourceImplTest {

    @Mock
    private lateinit var bankingService: BankingService

    private val userInfoMapper = UserInfoDataNetworkMapper()
    private val transactionMapper = TransactionDataNetworkMapper()

    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        remoteDataSource = RemoteDataSourceImpl(
            userInfoMapper,
            transactionMapper,
            bankingService
        )
    }

    @Test
    fun test_getUserInfo_success() {
        val userInfoNetwork = TestDataGenerator.generateUserInfo()
        val transactions = TestDataGenerator.generateTransactions()
        val userIdentifier = "AEZ19EDH2QZ"

        val mockResponse = ResponseWrapper(
            userInfoNetwork,
            transactions
        )

        Mockito.`when`(bankingService.getUserInformation(userIdentifier))
            .thenReturn(Observable.just(mockResponse))

        remoteDataSource.getUserInfo(userIdentifier)
            .test()
            .assertSubscribed()
            .assertValue {
                val data = userInfoMapper.to(it)
                data == userInfoNetwork
            }
            .assertComplete()

        Mockito.verify(bankingService, times(1))
            .getUserInformation(userIdentifier)
    }

    @Test
    fun test_getUserInfo_error() {
        val userIdentifier = "AEZ19EDH2QZ"
        val errorMsg = "ERROR"

        Mockito.`when`(bankingService.getUserInformation(userIdentifier))
            .thenReturn(Observable.error(Throwable(errorMsg)))

        remoteDataSource.getUserInfo(userIdentifier)
            .test()
            .assertSubscribed()
            .assertError {
                it.message == errorMsg
            }
            .assertNotComplete()
    }

    @Test
    fun test_getUserTransactions_success() {
        val userInfoNetwork = TestDataGenerator.generateUserInfo()
        val transactions = TestDataGenerator.generateTransactions()
        val userIdentifier = "AEZ19EDH2QZ"
        val limit = 10

        val mockResponse = ResponseWrapper(
            userInfoNetwork,
            transactions
        )

        Mockito.`when`(bankingService.getUserInformation(userIdentifier))
            .thenReturn(Observable.just(mockResponse))

        remoteDataSource.getUserTransactions(userIdentifier, limit)
            .test()
            .assertSubscribed()
            .assertValue { transactionsList ->
                transactionsList.containsAll(
                    transactions.map { transactionMapper.from(it) }
                )
            }
            .assertComplete()
    }

    @Test
    fun test_getUserTransactions_error() {
        val userIdentifier = "AEZ19EDH2QZ"
        val errorMsg = "ERROR"
        val limit = 10

        Mockito.`when`(bankingService.getUserInformation(userIdentifier))
            .thenReturn(Observable.error(Throwable(errorMsg)))

        remoteDataSource.getUserTransactions(userIdentifier, limit)
            .test()
            .assertSubscribed()
            .assertError {
                it.message == errorMsg
            }
            .assertNotComplete()
    }

}