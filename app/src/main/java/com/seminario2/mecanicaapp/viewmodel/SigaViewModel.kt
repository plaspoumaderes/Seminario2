package com.seminario2.mecanicaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seminario2.mecanicaapp.model.*
import com.seminario2.mecanicaapp.services.APIClient
import com.seminario2.mecanicaapp.services.APILoginInterface
import com.seminario2.mecanicaapp.services.APISigaInterface
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SigaViewModel : ViewModel() {

    private var apiInterface: APISigaInterface? =
        APIClient.clientSiga?.create(APISigaInterface::class.java)
    val vehicleResponseMutable = MutableLiveData<Response<List<Vehicle>>>()
    val garageResponseMutable = MutableLiveData<Response<List<GarageModel>>>()
    val garageByCategoriaResponseMutable = MutableLiveData<Response<List<GarageModel>>>()
    val addVehicleResponseMutable = MutableLiveData<Response<Vehicle>>()
    var postInsertFixMutable = MutableLiveData<Response<FixModelResponse>>()
    var getListFixMutable = MutableLiveData<Response<List<FixModelResponse>>>()

    fun getVehiclesbyUserName(loginResponse: LoginResponse) {
        var call = apiInterface?.getVehiclesbyUserName(loginResponse)
        call?.let { callResponse ->
            callResponse.enqueue(object : Callback<List<Vehicle>?> {
                override fun onResponse(
                    call: Call<List<Vehicle>?>,
                    response: Response<List<Vehicle>?>
                ) {
                    response.body()?.let { body ->
                        vehicleResponseMutable.value = Response.success(body)
                    } ?: run {
                        responseError("No se encontraron vehiculos")
                    }
                }

                override fun onFailure(call: Call<List<Vehicle>?>, t: Throwable) {
                    responseError("No se encontraron vehiculos")
                }
            })
        }
    }

    fun insertVehicle(vehicle: Vehicle) {
        var call = apiInterface?.postInsertVehicle(vehicle)
        call?.let { callResponse ->
            callResponse.enqueue(object : Callback<Vehicle?> {
                override fun onResponse(
                    call: Call<Vehicle?>,
                    response: Response<Vehicle?>
                ) {
                    response.body()?.let { body ->
                        addVehicleResponseMutable.value = Response.success(body)
                    } ?: run {
                        responseError("No se encontraron vehiculos")
                    }
                }

                override fun onFailure(call: Call<Vehicle?>, t: Throwable) {
                    responseError("No se encontraron vehiculos")
                }
            })
        }
    }

    fun getGarages() {
        var call = apiInterface?.getGarages()
        call?.let { callResponse ->
            callResponse.enqueue(object : Callback<List<GarageModel>?> {
                override fun onResponse(
                    call: Call<List<GarageModel>?>,
                    response: Response<List<GarageModel>?>
                ) {
                    response.body()?.let { body ->
                        garageResponseMutable.value = Response.success(body)
                    } ?: run {
                        responseError("No se encontraron vehiculos")
                    }
                }

                override fun onFailure(call: Call<List<GarageModel>?>, t: Throwable) {
                    responseError("No se encontraron vehiculos")
                }
            })
        }
    }
    fun getGaragesByCategory(garageCategory: GarageCategoryModel) {
        var call = apiInterface?.getGaragesByCategory(garageCategory)
        call?.let { callResponse ->
            callResponse.enqueue(object : Callback<List<GarageModel>?> {
                override fun onResponse(
                    call: Call<List<GarageModel>?>,
                    response: Response<List<GarageModel>?>
                ) {
                    response.body()?.let { body ->
                        garageByCategoriaResponseMutable.value = Response.success(body)
                    } ?: run {
                        responseError("No se encontraron vehiculos")
                    }
                }

                override fun onFailure(call: Call<List<GarageModel>?>, t: Throwable) {
                    responseError("No se encontraron vehiculos")
                }
            })
        }
    }

    fun postInsertFix(fixModel: FixModel) {
        var call = apiInterface?.postInsertFix(fixModel)
        call?.let { callResponse ->
            callResponse.enqueue(object : Callback<FixModelResponse?> {
                override fun onResponse(
                    call: Call<FixModelResponse?>,
                    response: Response<FixModelResponse?>
                ) {
                    response.body()?.let { body ->
                        postInsertFixMutable.value = Response.success(body)
                    } ?: run {
                        responseError("No se encontraron vehiculos")
                    }
                }

                override fun onFailure(call: Call<FixModelResponse?>, t: Throwable) {
                    responseError("No se encontraron vehiculos")
                }
            })
        }
    }

    fun getFixesby(loginResponse: LoginResponse) {
        var call = apiInterface?.getFixes(UserNameModel(loginResponse.userName))
        call?.let { callResponse ->
            callResponse.enqueue(object : Callback<List<FixModelResponse>?> {
                override fun onResponse(
                    call: Call<List<FixModelResponse>?>,
                    response: Response<List<FixModelResponse>?>
                ) {
                    response.body()?.let { body ->
                        getListFixMutable.value = Response.success(body)
                    } ?: run {
                        responseError("No se encontraron reparaciones")
                    }
                }

                override fun onFailure(call: Call<List<FixModelResponse>?>, t: Throwable) {
                    responseError("No se encontraron reparaciones")
                }
            })
        }
    }

    private fun responseError(messege: String) {
        val body: ResponseBody = ResponseBody.create(
            MediaType.parse("Error"), messege
        )
        vehicleResponseMutable.value = Response.error(500, body)
    }

}