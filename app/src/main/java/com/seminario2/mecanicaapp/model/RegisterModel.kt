package com.seminario2.mecanicaapp.model

class RegisterModel(
    var fullName: String,
    var email: String,
    var userName: String,
    var password: String,
    var password2: String,
    var address: String,
    var cellphone: String = "",
    val isMechanic: Boolean = false
)