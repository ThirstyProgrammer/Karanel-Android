package com.oqurystudio.karanel.android.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject

object NetworkUtil {

//    fun convertToRequestBody(json: MutableMap<String, Any>): RequestBody {
//        val jsonObject = Moshi.Builder().build().adapter(Map::class.java).toJson(json)
//        return RequestBody.create(MediaType.parse("application/json"), jsonObject)
//    }
//
//    fun convertToRequestBody(stringJson: String): RequestBody {
//        return RequestBody.create(MediaType.parse("application/json"), stringJson)
//    }

    fun createJsonRequestBody(vararg params: Pair<String, String>) =
        RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(mapOf(*params)).toString())

    fun createJsonRequestBodyWithAny(vararg params: Pair<String, Any>) =
        RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(mapOf(*params)).toString())
}