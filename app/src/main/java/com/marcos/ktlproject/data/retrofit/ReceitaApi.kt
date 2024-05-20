package com.marcos.ktlproject.data.retrofit

import com.marcos.ktlproject.data.pojo.ListaReceita
import retrofit2.Call
import retrofit2.http.GET

interface ReceitaApi {
    @GET("random.php")
    fun getRandomMeal():Call<ListaReceita>
}