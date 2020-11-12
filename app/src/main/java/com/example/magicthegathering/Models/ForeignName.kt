package com.example.magicthegathering.Models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForeignName(
    @SerializedName("flavor")
    val flavor: Any,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("multiverseid")
    val multiverseid: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("text")
    val text: String
): Serializable