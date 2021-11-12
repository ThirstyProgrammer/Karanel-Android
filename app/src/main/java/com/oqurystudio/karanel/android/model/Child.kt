package com.oqurystudio.karanel.android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class Child {

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
        val nik: String? = "",
        val name: String? = "",
        val gender: String? = "",
        @SerialName("birth_place") val birthPlace: String? = "",
        @SerialName("birth_date") val birthDate: String? = "",
        val blood: String? = "",
        @SerialName("child_order") val childOrder: Int? = 0,
        @SerialName("profile_image_id") val profileImgId: String? = "",
        @SerialName("profile_image_url") val profileImgUrl: String? = "",
        @SerialName("parent_id") val parentId: String? = "",
        @SerialName("created_at") val createdAt: String? = "",
        @SerialName("updated_at") val updatedAt: String? = "",
        @SerialName("deleted_at") val deletedAt: String? = "",
    )
}