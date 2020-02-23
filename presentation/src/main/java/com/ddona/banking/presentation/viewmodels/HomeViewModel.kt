package com.ddona.banking.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.ddona.banking.domain.entites.UserInfoEntity
import com.ddona.banking.domain.usecase.GetUserInfoTask
import com.ddona.banking.presentation.mapper.Mapper
import com.ddona.banking.presentation.model.Resource
import com.ddona.banking.presentation.model.UserInfo
import com.ddona.banking.presentation.qualifier.UserIdentity
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

import javax.inject.Inject

class HomeViewModel @Inject internal constructor(
    @UserIdentity private val userIdentifier: String,
    private val userInfoMapper: Mapper<UserInfoEntity, UserInfo>,
    private val getUserInfoTask: GetUserInfoTask
) : ViewModel() {

    val userInfoResource: LiveData<Resource<UserInfo>>
        get() = getUserInfoTask
            .buildUseCase(userIdentifier)
            .map { userInfoMapper.to(it) }
            .map { Resource.success(it) }
            .startWith(Resource.loading())
            .timeout(10000, TimeUnit.SECONDS)
            .onErrorResumeNext(
                Function {
                    Observable.just(Resource.error(it.localizedMessage))
                }
            ).toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
}
