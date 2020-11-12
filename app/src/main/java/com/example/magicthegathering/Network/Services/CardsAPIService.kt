package com.example.magicthegathering.Network.Services

import com.example.magicthegathering.Models.Cards
import com.example.magicthegathering.Network.Helpers.UrlBuilder
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CardsAPIService {
    @GET(UrlBuilder.GET_ALL_CARDS)
    fun getAllCards(): Single<Cards>

    @GET(UrlBuilder.GET_CARD_BY_ID)
    fun getCardById(
        @Query("id") id: Int
    ): Single<Cards>
}