package com.example.magicthegathering.Models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Cards(
    @SerializedName("cards")
    val cards: List<Card>
): Serializable