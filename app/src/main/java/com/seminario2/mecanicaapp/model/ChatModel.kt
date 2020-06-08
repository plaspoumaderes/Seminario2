package com.seminario2.mecanicaapp.model

import java.util.*

data class ChatModel(
    val fixId: String,
    val fullName: String,
    val body: String,
    val date: Date = Date(),
    val _id: String? = null
)