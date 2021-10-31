package com.oqurystudio.karanel.android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class DashboardPosyandu {

    @Serializable
    data class Response(
        override var stat_code: Int,
        override var stat_msg: String,
        override var meta: Meta?,
        val data: Data? = null
    ) : BaseResponseModel()

    @Serializable
    data class Data(
        val posyandu: Posyandu? = null,
        val parent: Int? = 0,
        val child: Int? = 0,
        val healthy: Int? = 0,
        val stunting: Int? = 0
    )

    @Serializable
    data class Posyandu(
        val id: String? = "",
        val name: String? = "",
        val address: String? = "",
        val city: String? = "",
        @SerialName("created_at") val createdAt: String? = "",
        @SerialName("updated_at") val updatedAt: String? = "",
        @SerialName("deleted_at") val deletedAt: String? = "",
    )
}