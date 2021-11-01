package com.oqurystudio.karanel.android.network

import com.oqurystudio.karanel.android.model.DashboardPosyandu
import com.oqurystudio.karanel.android.model.LoginParent
import com.oqurystudio.karanel.android.model.LoginPosyandu
import com.oqurystudio.karanel.android.model.Parents
import com.oqurystudio.karanel.android.util.Constant
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("/v1/api/admin/login")
    suspend fun loginPosyandu(
        @Body body: RequestBody
    ): LoginPosyandu.Response

    @POST("/v1/api/parent/login")
    suspend fun loginParent(
        @Body body: RequestBody
    ): LoginParent.Response

    @GET("/v1/api/dashboard/posyandu")
    suspend fun getDashboardPosyandu(
        @Header(Constant.NetworkConfig.AUTHORIZATION) value: String
    ): DashboardPosyandu.Response

    @GET("/v1/api/parent?page=1")
    suspend fun getParents(
        @Header(Constant.NetworkConfig.AUTHORIZATION) value: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("search") query: String
    ): Parents.Response
}