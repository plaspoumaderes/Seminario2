package com.seminario2.mecanicaapp.services

import com.seminario2.mecanicaapp.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST


interface APISigaInterface {

    companion object {
        var headersDefault = mapOf("Content-Type" to "application/json")
    }

    @GET("apiSIGA/getGarages")
    fun getGarages(@HeaderMap headers: Map<String, String> = headersDefault): Call<List<GarageModel>?>?

    @POST("apiSIGA/getGaragesbyName")
    fun getGaragesbyName(@Body garageModel: GarageModel, @HeaderMap headers: Map<String, String> = headersDefault): Call<GarageModel?>?

    @POST("apiSIGA/insertVehicle")
    fun postInsertVehicle(@Body vehicle: Vehicle, @HeaderMap headers: Map<String, String> = headersDefault): Call<Vehicle?>?

    @POST("apiSIGA/getVehiclesbyUserName")
    fun getVehiclesbyUserName(@Body loginResponse: LoginResponse, @HeaderMap headers: Map<String, String> = headersDefault): Call<List<Vehicle>?>?

    @POST("apiSIGA/insertFix")
    fun postInsertFix(@Body fixModel: FixModel, @HeaderMap headers: Map<String, String> = headersDefault): Call<FixModel?>?

}