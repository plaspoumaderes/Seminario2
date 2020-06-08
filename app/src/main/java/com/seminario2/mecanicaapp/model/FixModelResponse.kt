package com.seminario2.mecanicaapp.model

import java.util.*

data class FixModelResponse(
    var _id: String,
    var vehicleId: List<String>,
    var userName: String,
    var garageId: String,
    var fixVehicleKm: String,
    var fixType: String,
    var fixIngress: Date? = null,
    var fixEgress: Date? = null,
    var fixBudget: Int = 0,
    var fixFinalPrice: Int = 0,
    var fixStatus: String? = null,
    var fixStatusNumber: Int,
    var garage: GarageModel?,
    var vehicle: Vehicle?,
    var fixFinalPriceAccept: Boolean = false
) {
    fun getStatusName(): String {
        return when (fixStatusNumber) {
            2 -> "Recibido"
            3 -> "En reparacion"
            4 -> "Terminado"
            5 -> "Entregado"
            6 -> "Calificado"
            else -> "Espera de recepcion"
        }
    }
}
