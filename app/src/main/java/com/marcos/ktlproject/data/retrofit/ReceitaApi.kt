package com.marcos.ktlproject.data.retrofit


import com.marcos.ktlproject.data.pojo.Meal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ReceitaApi {
    @GET("random.php")
    fun getRandomMeal():Call<Meal>

    @GET("lookup.php?")
    fun getReceitaDetalhes(@Query("i")id:String):Call<Meal>

    @GET("filter.php?")
    fun getPopularReceita(@Query("c")categoryName:String):Call<com.marcos.ktlproject.data.pojo.category.Meal>
}