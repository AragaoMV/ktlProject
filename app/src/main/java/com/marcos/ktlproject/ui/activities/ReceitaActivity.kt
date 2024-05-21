package com.marcos.ktlproject.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.marcos.ktlproject.R
import com.marcos.ktlproject.data.pojo.receitas.Meals
import com.marcos.ktlproject.databinding.ActivityReceitaBinding
import com.marcos.ktlproject.ui.fragments.HomeFragment
import com.marcos.ktlproject.viewModel.ReceitaDetalheViewModel

class ReceitaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceitaBinding
    private lateinit var receitaId:String
    private lateinit var receitaName:String
    private lateinit var receitaThumb:String
    private lateinit var receitaMvvm:ReceitaDetalheViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceitaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        receitaMvvm = ViewModelProvider(this)[ReceitaDetalheViewModel::class.java]
        getRandomReceitaInfoFromIntent()
        setInformationInViews()
        loading()
        receitaMvvm.getReceitaDetalhe(receitaId)
        observerReceitaDeralheLiveData()
    }

    private fun observerReceitaDeralheLiveData() {
        receitaMvvm.observeReceitaDetalheLiveData().observe(this, object : Observer<Meals>{
            override fun onChanged(value: Meals) {
               val receita = value
                onResponse()
                binding.descCategotiaReceita.text = "Category: ${receita!!.strCategory}"
                binding.descLocalReceita.text = "Region: ${receita.strArea}"
                binding.modopreparo.text = "Instruction: ${receita.strInstructions}"

            }
        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(receitaThumb)
            .into(binding.fotoReceitaDetalhes)

        binding.collapsingToolbar.title = receitaName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.black))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getRandomReceitaInfoFromIntent(){
        val intent = intent
        receitaId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        receitaName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        receitaThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loading(){
        binding.modopreparo.visibility = View.INVISIBLE
        binding.tituloDetalhes.visibility = View.INVISIBLE
        binding.descCategotiaReceita.visibility = View.INVISIBLE
        binding.descLocalReceita.visibility = View.INVISIBLE
        binding.progressbar.visibility = View.VISIBLE
    }
    private fun onResponse(){
        binding.modopreparo.visibility = View.VISIBLE
        binding.tituloDetalhes.visibility = View.VISIBLE
        binding.descCategotiaReceita.visibility = View.VISIBLE
        binding.descLocalReceita.visibility = View.VISIBLE
        binding.progressbar.visibility = View.INVISIBLE
    }

}