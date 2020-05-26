package com.seminario2.mecanicaapp.model

import java.util.*

data class FixModel(
    var vehicleId: String,
    var userName: String,
    var garageId: String,
    var fixVehicleKm: String,
    var fixType: String,
    var fixIngress: Date? = null,
    var fixEgress: Date? = null,
    var fixBudget: Int = 0,
    var fixFinalPrice: Int = 0,
    var fixStatus: String? = null,
    var fixStatusNumber: Int = 1
)