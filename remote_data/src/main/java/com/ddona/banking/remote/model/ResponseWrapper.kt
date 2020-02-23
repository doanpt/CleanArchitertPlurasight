package com.ddona.banking.remote.model

import com.google.gson.annotations.SerializedName

class ResponseWrapper(
    @SerializedName("account-info") val userInfo: UserInfoNetwork,
    @SerializedName("transactions") val transactions: List<TransactionNetwork>
)