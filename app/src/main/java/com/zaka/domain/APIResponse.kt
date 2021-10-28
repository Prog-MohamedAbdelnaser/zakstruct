package com.zaka.domain

import com.google.gson.annotations.SerializedName
import com.zaka.domain.DomainConstants.CODE
import com.zaka.domain.DomainConstants.MESSAGE
import com.zaka.domain.DomainConstants.PAYLOAD
import com.zaka.domain.DomainConstants.STATUS

private const val SUCCESS_STATUS = "1"

data class APIResponse<P>(
    @SerializedName(STATUS) var status: String,
    @SerializedName(CODE) var code: String,
    @SerializedName(MESSAGE) var message: String,
    @SerializedName(PAYLOAD) var payload: P?)
{

    fun isSuccessful(): Boolean {
        return status == SUCCESS_STATUS
    }

}