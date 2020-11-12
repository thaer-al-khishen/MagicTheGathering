package com.example.diverseapp.Models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Cards(
    @SerializedName("cards")
    val cards: List<Card>
): Serializable