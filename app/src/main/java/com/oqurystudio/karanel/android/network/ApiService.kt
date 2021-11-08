package com.oqurystudio.karanel.android.network

import com.oqurystudio.karanel.android.model.*
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

    @GET("/v1/api/parent/id/{id_parent}")
    suspend fun getParent(
        @Header(Constant.NetworkConfig.AUTHORIZATION) value: String,
        @Path("id_parent") idParent: String
    ): Parent.Response

    @GET("/v1/api/parent/token")
    suspend fun getParentByToken(
        @Header(Constant.NetworkConfig.AUTHORIZATION) value: String,
    ): Parent.Response

    @POST("/v1/api/parent")
    suspend fun submitParent(
        @Header(Constant.NetworkConfig.AUTHORIZATION) value: String,
        @Body body: RequestBody
    ): FormParent.Response

    @POST("/v1/api/child")
    suspend fun submitChild(
        @Header(Constant.NetworkConfig.AUTHORIZATION) value: String,
        @Body body: RequestBody
    ): FormChild.Response
}