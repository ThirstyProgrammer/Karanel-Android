package com.oqurystudio.karanel.android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class LoginPosyandu {

    data class Request(
        var email: String = "",
        var password: String = ""
    )

    @Serializable
    data class Response(
        override var stat_code: Int,
        override var stat_msg: String,
        val data: Data? = null
    ) : BaseResponseModel()

    @Serializable
    data class Data(
        val token: String? = "",
        @SerialName("expired_date") val expDate: String,
        @SerialName("refresh_token") val refreshToken: String,
        @SerialName("refresh_expired_date") val refreshExpDate: String,
    )
}