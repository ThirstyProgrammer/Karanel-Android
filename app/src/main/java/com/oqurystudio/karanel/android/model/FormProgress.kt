package com.oqurystudio.karanel.android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class FormProgress {

    data class Payload(
        var parentId: String = "",
        var childId: String = "",
        var growthDate: String = "",
        var record: Record = Record()
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
        @SerialName("child_id") val childId: String? = "",
    )
}