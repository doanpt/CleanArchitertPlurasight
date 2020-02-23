package com.ddona.banking.domain.usecase

import com.ddona.banking.domain.entites.UserInfoEntity
import com.ddona.banking.domain.qualifiers.Background
import com.ddona.banking.domain.qualifiers.Foreground
import com.ddona.banking.domain.repository.BankingRepository
import com.ddona.banking.domain.usecase.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetUserInfoTask @Inject constructor(
    private val bankingRepository: BankingRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) : ObservableUseCase<UserInfoEntity, String>(backgroundScheduler, foregroundScheduler) {

    override fun generateObservable(input: String?): Observable<UserInfoEntity> {
        if (input == null) {
            throw IllegalArgumentException("User identifier can't be null")
        }
        return bankingRepository.getUserInfo(input)
    }

}