package com.example.headsupcustomversion

import java.io.Serializable

data class CelebritiesItem(
    val pk: Int,
    val name: String,
    val taboo1: String,
    val taboo2: String,
    val taboo3: String
): Serializable