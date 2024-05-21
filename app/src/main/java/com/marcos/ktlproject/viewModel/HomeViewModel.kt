package com.marcos.ktlproject.viewModel



import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcos.ktlproject.data.pojo.Meal
import com.marcos.ktlproject.data.pojo.Meals
import com.marcos.ktlproject.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {

    private var randomReceitaLiveData = MutableLiveData<Meals>()

    fun getRandomReceita() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<Meal> {
            override fun onResponse(call: Call<Meal>, response: Response<Meal>) {
                if (response.body() != null) {
                    val randomReceita: Meals = response.body()!!.meals[0]
                    randomReceitaLiveData.value = randomReceita
                    Log.d("API", "ID ${randomReceita.idMeal}E NOME${randomReceita.strMeal} DA API ")
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })

        }
    fun observeRandomReceitaLiveData():LiveData<Meals>{
        return randomReceitaLiveData
    }
    }

