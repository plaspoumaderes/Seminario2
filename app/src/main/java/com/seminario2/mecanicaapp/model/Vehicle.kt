package com.seminario2.mecanicaapp.model

class Vehicle(
    val username: String,
    val vehiclePlate: String,
    val vehicleBrand: String,
    val vehicleModel: String,
    val vehicleYear: Int,
    val vehicleColor: String,
    val vehicleType: String,
    val vehicleChassisSN: String,
    val vehicleEngineSN: String,
    val vehiclePhoto: String = "",
    var status: Int = 0
)