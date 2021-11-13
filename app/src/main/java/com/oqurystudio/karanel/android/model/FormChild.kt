package com.oqurystudio.karanel.android.model

import kotlinx.serialization.Serializable

class FormChild {

    data class Payload(
        var name: String = "",
        var gender: String = "Laki-laki",
        var birthPlace: String = "",
        var birthDate: String = "",
        var birthType: String = "Tunggal",
        var bloodType: String = "A",
        var childOrder: Int = 0,
        var parentId: String = "",
        var record: Record = Record()
    )

    @Serializable
    data class Response(
        override var stat_code: Int,
        override var stat_msg: String,
        override var meta: Meta?,
        val data: FormParent.Data? = null
    ) : BaseResponseModel()
}