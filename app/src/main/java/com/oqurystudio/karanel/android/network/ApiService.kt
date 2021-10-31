package com.oqurystudio.karanel.android.network

import com.oqurystudio.karanel.android.model.LoginParent
import com.oqurystudio.karanel.android.model.LoginPosyandu
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/v1/api/admin/login")
    suspend fun loginPosyandu(
        @Body body: RequestBody
    ): LoginPosyandu.Response

    @POST("/v1/api/parent/login")
    suspend fun loginParent(
        @Body body: RequestBody
    ): LoginParent.Response
}