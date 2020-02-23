package com.ddona.banking.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ddona.banking.domain.repository.BankingRepository
import com.ddona.banking.domain.usecase.GetUserInfoTask
import com.ddona.banking.presentation.mapper.UserInfoEntityMapper
import com.ddona.banking.presentation.model.Status
import com.ddona.banking.presentation.utils.TestDataGenerator
import com.ddona.banking.presentation.viewmodels.HomeViewModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class HomeViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: BankingRepository

    private lateinit var homeVM: HomeViewModel
    private val userInfoMapper = UserInfoEntityMapper()

    private val userInfo = TestDataGenerator.generateUserInfo()
    private val userInfoEntity = userInfoMapper.from(userInfo)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        val getUserInfoTask = GetUserInfoTask(
            repository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )

        homeVM = HomeViewModel(
            userInfo.accountNumber,
            userInfoMapper,
            getUserInfoTask
        )
    }

    @Test
    fun test_getUserInfo_success() {

        Mockito.`when`(repository.getUserInfo(anyString()))
            .thenReturn(Observable.just(userInfoEntity))

        val userInfoResource = homeVM.userInfoResource

        userInfoResource.observeForever { /*Do nothing*/ }

        assertTrue(
            userInfoResource.value?.status == Status.SUCCESS
                && userInfoResource.value?.data == userInfo
        )
    }

    @Test
    fun test_getUserInfo_error() {
        val errorMsg = "user info error in fetching data"
        Mockito.`when`(repository.getUserInfo(anyString()))
            .thenReturn(Observable.error(Throwable(errorMsg)))

        val userInfoResource = homeVM.userInfoResource

        userInfoResource.observeForever { /*Do nothing*/ }

        assertTrue(
            userInfoResource.value?.status == Status.ERROR
                && userInfoResource.value?.message == errorMsg
        )
    }

}