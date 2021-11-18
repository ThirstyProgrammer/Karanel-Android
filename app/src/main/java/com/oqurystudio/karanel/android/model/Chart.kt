package com.oqurystudio.karanel.android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class Chart {

    @Serializable
    data class Response(
        override var stat_code: Int,
        override var stat_msg: String,
        override var meta: Meta? = null,
        val data: Data? = null
    ) : BaseResponseModel()

    @Serializable
    data class Data(
        @SerialName("image_path") var imgPath: String? = "",
        val weight: Double? = 0.0,
        val height: Double? = 0.0,
        val age: String? = "",
        val status: String? = "",
        val records: List<Record>? = emptyList()
    )

    @Serializable
    data class Record(
        var id: String? = "",
        var month: Int? = 0,
        var weight: Double? = 0.0,
        var height: Double? = 0.0,
        @SerialName("head_circumference") var headCircumference: Double? = 0.0,
        var status: String? = ""
    )
}