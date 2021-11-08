package com.oqurystudio.karanel.android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class FormParent {

    data class Payload(
        var motherNIK: String = "",
        var motherName: String = "",
        var motherWork: String = "",
        var motherPhone: String = "",
        var fatherNIK: String = "",
        var fatherName: String = "",
        var fatherWork: String = "",
        var fatherPhone: String = "",
        var address: String = ""
    )

    @Serializable
    data class Response(
        override var stat_code: Int,
        override var stat_msg: String,
        override var meta: Meta?,
        val data: Data? = null
    ) : BaseResponseModel()

    @Serializable
    data class Data(
        val id: String? = "",
        @SerialName("id_karnel") val idKarnel: String? = ""
    )
}
