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

    suspend fun submitParent(token: String, payload: FormParent.Payload): FormParent.Response {
        return service.submitParent(
            generateBearerToken(token), NetworkUtil.createJsonRequestBody(
                "name_mother" to payload.motherName,
                "job_mother" to payload.motherWork,
                "phone_mother" to payload.motherPhone,
                "name_father" to payload.fatherName,
                "job_father" to payload.fatherWork,
                "phone_father" to payload.fatherPhone,
                "address" to payload.address,
                "nik_mother" to payload.nik,
                "nik_father" to payload.nik,
            )
        )
    }

    suspend fun submitChild(token: String, payload: FormChild.Payload): FormChild.Response {
        // TODO Update Payload Generator
        return service.submitChild(
            generateBearerToken(token),
            NetworkUtil.createJsonRequestBodyWithAny(
                "name" to payload.name,
                "gender" to payload.gender,
                "birth_place" to payload.birthPlace,
                "birth_date" to payload.birthDate,
                "blood" to payload.bloodType,
                "child_order" to payload.childOrder
            )
        )
    }

    private fun generateBearerToken(token: String): String = "Bearer $token"
}