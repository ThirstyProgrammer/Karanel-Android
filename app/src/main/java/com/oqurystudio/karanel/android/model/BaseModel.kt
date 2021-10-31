package com.oqurystudio.karanel.android.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

abstract class BaseResponseModel {
    abstract var stat_code: Int
    abstract var stat_msg: String
    abstract var meta: Meta?
}

@Serializable
data class Meta(
    @SerialName("current_page")
    val current_page: Int = 0,
    @SerialName("last_page")
    val last_page: Int = 0,
    @SerialName("record_per_page")
    val recordPerPage: Int = 0,
    val count: Int = 0
)