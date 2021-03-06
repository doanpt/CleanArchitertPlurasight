package com.ddona.banking.local

import com.ddona.banking.data.repository.LocalDataSource
import com.ddona.banking.local.database.TransactionDAO
import com.ddona.banking.local.database.UserInfoDAO
import com.ddona.banking.local.mapper.TransactionDataLocalMapper
import com.ddona.banking.local.mapper.UserInfoDataLocalMapper
import com.ddona.banking.local.source.LocalDataSourceImpl
import com.ddona.banking.local.utils.TestDataGenerator
import io.reactivex.Completable
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
class LocalDataSourceUserInfoTest {

    private val userInfoMapper = UserInfoDataLocalMapper()
    private val transactionMapper = TransactionDataLocalMapper()

    @Mock
    private lateinit var userInfoDao: UserInfoDAO

    @Mock
    private lateinit var transactionDao: TransactionDAO

    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        localDataSource = LocalDataSourceImpl(
            userInfoMapper,
            transactionMapper,
            userInfoDao,
            transactionDao
        )
    }

    @Test
    fun test_getUserInfo_success() {
        val userIdentifier = "1BFC9A38E6C7"
        val userInfoLocal = TestDataGenerator.generateUserInfo()

        Mockito.`when`(userInfoDao.getUserInfo(userIdentifier))
            .thenReturn(Observable.just(userInfoLocal))

        localDataSource.getUserInfo(userIdentifier)
            .test()
            .assertSubscribed()
            .assertValue { it == userInfoMapper.from(userInfoLocal) }
    }

    @Test
    fun test_saveUserInfo_success() {
        val userInfoLocal = TestDataGenerator.generateUserInfo()

        localDataSource.saveUserInfo(
            userInfoMapper.from(userInfoLocal)
        )

        Mockito.verify(userInfoDao, times(1))
            .addUserInfo(userInfoLocal)
    }
}
