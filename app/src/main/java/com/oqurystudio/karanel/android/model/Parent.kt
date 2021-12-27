package com.oqurystudio.karanel.android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

class Parent {

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
        val address: String? = "",
        @SerialName("id_karnel") val idKarnel: String? = "",
        @SerialName("image_karnel_path") val imgUrl: String? = "",
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
        @SerialName("child") val children: List<Child>? = arrayListOf(),
        @SerialName("created_at") val createdAt: String? = "",
        @SerialName("updated_at") val updatedAt: String? = "",
        @SerialName("deleted_at") val deletedAt: String? = "",
        val posyandu : Posyandu? = null,
        @Transient
        val typeItem: Int = 0
    )

    @Serializable
    data class Child(
        val id: String? = "",
        val name: String? = "",
        val gender: String? = "",
        @SerialName("birth_place") val birthPlace: String? = "",
        @SerialName("birth_date") val birthDate: String? = "",
        val blood: String? = "",
        @SerialName("child_order") val childOrder: Int? = 0,
        @SerialName("profile_image_id") val profileImgId: String? = "",
        @SerialName("profile_image_url") val profileImgUrl: String? = "",
        @SerialName("parent_id") val parentId: String? = "",
        @SerialName("status_gizi") val status: String? = "",
        @SerialName("created_at") val createdAt: String? = "",
        @SerialName("updated_at") val updatedAt: String? = "",
        @SerialName("deleted_at") val deletedAt: String? = "",
    )

    @Serializable
    data class Posyandu(
        @SerialName("phone_number") val phoneNumber: String? = ""
    )
}
