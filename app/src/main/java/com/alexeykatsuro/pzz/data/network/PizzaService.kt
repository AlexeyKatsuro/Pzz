package com.alexeykatsuro.pzz.data.network

import com.alexeykatsuro.pzz.data.dto.PizzaResponse
import retrofit2.http.GET

interface PizzaService {

    @GET("api/v1/pizzas?load=ingredients,filters&filter=meal_only:0&order=position:asc")
    suspend fun getPizza(): PizzaResponse

}