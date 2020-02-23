package com.ddona.banking.remote.api

import com.ddona.banking.remote.model.ResponseWrapper
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface BankingService {

    @GET("assets/bankassist/{identifier}.json")
    fun getUserInformation(@Path("identifier") userIdentifier: String):
        Observable<ResponseWrapper>
}