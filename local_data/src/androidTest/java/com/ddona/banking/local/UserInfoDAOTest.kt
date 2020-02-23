package com.ddona.banking.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.ddona.banking.local.database.BankBuddyDatabase
import com.ddona.banking.local.database.UserInfoDAO
import com.ddona.banking.local.utils.TestData
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class UserInfoDAOTest {

    private lateinit var bankBuddyDB: BankBuddyDatabase
    private lateinit var userInfoDAO: UserInfoDAO

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        bankBuddyDB = Room.inMemoryDatabaseBuilder(context, BankBuddyDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        userInfoDAO = bankBuddyDB.getUserInfoDao()
    }

    @After
    fun tearDown() {
        bankBuddyDB.close()
    }

    @Test
    fun test_saveAndRetrieveUserInfo() {
        val userInfo = TestData.generateUserInfo()

        userInfoDAO.addUserInfo(userInfo)

        userInfoDAO.getUserInfo(userInfo.accountNumber)
            .test()
            .assertValue {
                it == userInfo
            }.assertNotComplete() // As Room Observables are kept alive
    }

    @Test
    fun test_clearCachedUserInfo() {
        val userInfo = TestData.generateUserInfo()

        userInfoDAO.addUserInfo(userInfo)

        userInfoDAO.getUserInfo(userInfo.accountNumber)
            .test()
            .assertValueCount(1)
            .assertValue {
                it == userInfo
            }

        userInfoDAO.clearCachedUserInfo().subscribe()

        userInfoDAO.getUserInfo(userInfo.accountNumber)
            .test()
            .assertNoValues()
            .assertNotComplete()
    }

}