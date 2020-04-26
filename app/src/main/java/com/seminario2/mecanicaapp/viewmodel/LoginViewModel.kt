package com.seminario2.mecanicaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seminario2.mecanicaapp.model.LoginResponse
import com.seminario2.mecanicaapp.model.RegisterModel
import com.seminario2.mecanicaapp.services.APIClient
import com.seminario2.mecanicaapp.services.APIInterface
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private var apiInterface: APIInterface? = APIClient.client?.create(APIInterface::class.java)

    val loginResponseMutable = MutableLiveData<Response<LoginResponse>>()
    val registerResponseMutable = MutableLiveData<Response<LoginResponse>>()

    fun login(username: String, password: String) {
        var call = apiInterface?.getLogin(username, password)
        call?.let { callResponse ->
            callResponse.enqueue(object : Callback<LoginResponse?> {
                override fun onResponse(
                    call: Call<LoginResponse?>,
                    response: Response<LoginResponse?>
                ) {
                    response.body()?.let { body ->
                        loginResponseMutable.value = Response.success(body)
                    } ?: run {
                        responseError("Usuario y/o Contraseña invalida")
                    }
                }

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    responseError("Usuario y/o Contraseña invalida")
                }
            })
        }
    }

    fun register(registerModel: RegisterModel) {
        var call = apiInterface?.postRegister(registerModel)
        call?.let { callResponse ->
            callResponse.enqueue(object : Callback<LoginResponse?> {
                override fun onResponse(
                    call: Call<LoginResponse?>,
                    response: Response<LoginResponse?>
                ) {
                    response.body()?.let { body ->
                        loginResponseMutable.value = Response.success(body)
                    } ?: run {
                        responseError("Usuario y/o Contraseña invalida")
                    }
                }

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    responseError("Usuario y/o Contraseña invalida")
                }
            })
        }
    }

    private fun responseError(messege: String) {
        val body: ResponseBody = ResponseBody.create(
            MediaType.parse("Error"), messege
        )
        loginResponseMutable.value = Response.error(500, body)
    }
}