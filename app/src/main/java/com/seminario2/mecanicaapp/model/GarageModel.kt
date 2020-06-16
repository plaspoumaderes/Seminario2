package com.seminario2.mecanicaapp.model

class GarageModel(
    val _id: String,
    val garageEmail: String,
    val garageCategory: String,
    val garageCuit: String,
    val garageName: String,
    val garageAddress: String,
    val garagePhoneNumber: String?,
    val garageVehicleCapacity: Int,
    val stars: Double,
    val distance: String
)