package com.seminario2.mecanicaapp.services

import com.seminario2.mecanicaapp.model.LoginModel
import com.seminario2.mecanicaapp.model.LoginResponse
import com.seminario2.mecanicaapp.model.RegisterModel
import retrofit2.Call
import retrofit2.http.*


interface APIInterface {

    companion object {
        var headersDefault = mapOf("Content-Type" to "application/json")
    }

    @POST("login")
    fun getLogin(@Body body: LoginModel, @HeaderMap headers: Map<String, String> = headersDefault): Call<LoginResponse?>?

    @POST("register")
    fun postRegister(@Body body: RegisterModel, @HeaderMap headers: Map<String, String> = headersDefault): Call<LoginResponse?>?

}