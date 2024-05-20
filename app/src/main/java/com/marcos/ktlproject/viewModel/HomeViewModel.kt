package com.marcos.ktlproject.viewModel



import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcos.ktlproject.data.pojo.ListaReceita
import com.marcos.ktlproject.data.pojo.ReceitaDetail
import com.marcos.ktlproject.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {

    private var randomReceitaLiveData = MutableLiveData<ReceitaDetail>()

    fun getRandomReceita() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<ListaReceita> {
            override fun onResponse(call: Call<ListaReceita>, response: Response<ListaReceita>) {
                if (response.body() != null) {
                    val randomReceita: ReceitaDetail = response.body()!!.receitaDetails[0]
                    randomReceitaLiveData.value = randomReceita
                    Log.d("API", "ID ${randomReceita.idMeal}E NOME${randomReceita.strMeal} DA API ")
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<ListaReceita>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })

        }
    fun observeRandomReceitaLiveData():LiveData<ReceitaDetail>{
        return randomReceitaLiveData
    }
    }

