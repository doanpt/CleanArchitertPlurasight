package com.ddona.banking.domain.usercase

import com.ddona.banking.domain.repository.BankingRepository
import com.ddona.banking.domain.usecase.TransactionStatusUpdaterTask
import com.ddona.banking.domain.utils.TestDataGenerator
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class TransactionStatusUpdaterTaskTest {

    private lateinit var transactionStatusUpdaterTask: TransactionStatusUpdaterTask

    @Mock
    private lateinit var bankingRepository: BankingRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        transactionStatusUpdaterTask = TransactionStatusUpdaterTask(
            bankingRepository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }

    @Test
    fun test_transactionStatusUpdaterTask_success() {
        val transaction = TestDataGenerator.generateTransactions()[0]

        Mockito.`when`(bankingRepository.updateTransaction(transaction))
            .thenReturn(Completable.complete())

        val testObserver = transactionStatusUpdaterTask.buildUseCase(
            transaction
        ).test()

        Mockito.verify(bankingRepository, Mockito.times(1))
            .updateTransaction(transaction)

        testObserver
            .assertSubscribed()
            .assertComplete()
    }

    @Test
    fun test_transactionStatusUpdaterTask_error() {
        val transaction = TestDataGenerator.generateTransactions()[0]
        val errorMsg = "ERROR OCCURRED"

        Mockito.`when`(bankingRepository.updateTransaction(transaction))
            .thenReturn(Completable.error(Throwable(errorMsg)))

        val testObserver = transactionStatusUpdaterTask
            .buildUseCase(transaction).test()

        Mockito.verify(bankingRepository, Mockito.times(1))
            .updateTransaction(transaction)

        testObserver
            .assertSubscribed()
            .assertError { it.message?.equals(errorMsg) ?: false }
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_transactionStatusUpdaterTaskNoParameters_error() {
        val testObserver = transactionStatusUpdaterTask.buildUseCase().test()
        testObserver.assertSubscribed()
    }

}