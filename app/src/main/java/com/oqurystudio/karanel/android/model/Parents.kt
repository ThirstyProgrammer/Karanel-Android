package com.oqurystudio.karanel.android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

class Parents {

    @Serializable
    data class Response(
        override var stat_code: Int,
        override var stat_msg: String,
        override var meta: Meta?,
        val data: List<Data>? = null
    ) : BaseResponseModel()

    @Serializable
    data class Data(
        val id: String? = "",
        val address: String? = "",
        @SerialName("id_karnel") val idKarnel: String? = "",
        @SerialName("name_mother") val motherName: String? = "",
        @SerialName("job_mother") val motherJob: String? = "",
        @SerialName("phone_mother") val motherPhone: String? = "",
        @SerialName("name_father") val fatherName: String? = "",
        @SerialName("job_father") val fatherJob: String? = "",
        @SerialName("phone_father") val fatherPhone: String? = "",
        @SerialName("role_name") val role: String? = "",
        @SerialName("profile_image_id") val profileImgId: String? = "",
        @SerialName("profile_image") val profileImg: String? = "",
        @SerialName("nik_mother") val motherNIK: String? = "",
        @SerialName("nik_father") val fatherNIK: String? = "",
        @SerialName("count_child") val totalChild: Int? = 0,
        @SerialName("created_at") val createdAt: String? = "",
        @SerialName("updated_at") val updatedAt: String? = "",
        @SerialName("deleted_at") val deletedAt: String? = "",
        @Transient
        val typeItem: Int = 0
    )


}