package com.seminario2.mecanicaapp.services

import com.seminario2.mecanicaapp.model.LoginResponse
import com.seminario2.mecanicaapp.model.RegisterModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface APIInterface {

    @GET("login")
    fun getLogin(@Query("username") username: String?, @Query("password") password: String?): Call<LoginResponse?>?

    @POST("register")
    fun postRegister(@Body body: RegisterModel): Call<LoginResponse?>?

}