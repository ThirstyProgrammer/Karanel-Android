package com.oqurystudio.karanel.android.repository

import com.oqurystudio.karanel.android.model.*
import com.oqurystudio.karanel.android.network.ApiService
import com.oqurystudio.karanel.android.util.NetworkUtil
import javax.inject.Inject

class KaranelRepository @Inject constructor(private val service: ApiService) {

    suspend fun loginPosyandu(body: LoginPosyandu.Request): LoginPosyandu.Response {
        return service.loginPosyandu(NetworkUtil.createJsonRequestBody("email" to body.email, "password" to body.password))
    }

    suspend fun loginParent(body: LoginParent.Request): LoginParent.Response {
        return service.loginParent(NetworkUtil.createJsonRequestBody("id_karnel" to body.idParent))
    }

    suspend fun getDashboardPosyandu(token: String): DashboardPosyandu.Response {
        return service.getDashboardPosyandu(generateBearerToken(token))
    }

    suspend fun getParents(token: String, page: Int, limit: Int, query: String): Parents.Response {
        return service.getParents(generateBearerToken(token), page, limit, query)
    }

    suspend fun getParent(token: String, idParent: String): Parent.Response {
        return service.getParent(generateBearerToken(token), idParent)
    }

    private fun generateBearerToken(token: String): String = "Bearer $token"
}