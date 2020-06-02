package com.seminario2.mecanicaapp.model

import java.util.*

data class Comment(
    val garageId: String,
    val fullName: String,
    val date: Date = Date(),
    var stars: Int = 0,
    var title: String = "",
    var body: String = ""
)