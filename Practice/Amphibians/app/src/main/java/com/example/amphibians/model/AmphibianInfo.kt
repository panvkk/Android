package com.example.amphibians.model

import kotlinx.serialization.SerialName

data class AmphibianInfo (
    val id: String,
    @SerialName(value = "img_src")
    val imgSrc: String,
    val title: String,
    val desc: String
)