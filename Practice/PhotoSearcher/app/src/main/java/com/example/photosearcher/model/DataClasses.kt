package com.example.photosearcher.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val items: List<Image>
)

@Serializable
data class Image(
    val link: String
)

//@Serializable
//data class Item(
//    val pagemap: PageMap? = null
//)
//
//@Serializable
//data class PageMap(
//    @SerialName(value = "cse_image")
//    val cseImage: List<CseImage>? = null
//)
//
//@Serializable
//data class CseImage(
//    val src: String
//)
//
//@Serializable
//data class Items(
//    val items: List<Image>
//)