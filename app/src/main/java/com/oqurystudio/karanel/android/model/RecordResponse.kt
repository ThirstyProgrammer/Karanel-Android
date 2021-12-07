package com.oqurystudio.karanel.android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class RecordResponse {

    @Serializable
    data class Response(
        override var stat_code: Int,
        override var stat_msg: String,
        override var meta: Meta? = null,
        val data: Data? = null
    ) : BaseResponseModel()

    @Serializable
    data class Data(
        val id: String? = "",
        @SerialName("growth_date") var growthDate: String = "",
        var month: Int = 0,
        var weight: Double = 0.0,
        var height: Double = 0.0,
        @SerialName("head_circumference") var headCircumference: Double = 0.0
    )
}