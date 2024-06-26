package com.marcos.ktlproject.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.marcos.ktlproject.adapters.PopularAdapter
import com.marcos.ktlproject.data.pojo.Meals
import com.marcos.ktlproject.databinding.FragmentHomeBinding
import com.marcos.ktlproject.ui.activities.ReceitaActivity
import com.marcos.ktlproject.viewModel.HomeViewModel



class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meals
    private lateinit var popmeal: Meals
    private lateinit var popItemsAdapter: PopularAdapter

    companion object {
        const val MEAL_ID = "com.marcos.ktlproject.ui.fragments.idMeal"
        const val MEAL_NAME = "com.marcos.ktlproject.ui.fragments.nameMeal"
        const val MEAL_THUMB = "com.marcos.ktlproject.ui.fragments.thumbsMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]

        popItemsAdapter = PopularAdapter()
        onPopItemClick()

    }

    private fun onPopItemClick() {
        popItemsAdapter.onItemClick = { popmeal ->
            val intent = Intent(activity, ReceitaActivity::class.java)
            intent.putExtra(MEAL_ID, popmeal.idMeal)
            intent.putExtra(MEAL_NAME, popmeal.strMeal)
            intent.putExtra(MEAL_THUMB, popmeal.strMealThumb)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopItemRV()

        homeMvvm.getRandomReceita()
        observeRandomMeal()
        onRandomReceitaClick()

        homeMvvm.getPopuplarItems()
        observePopularItemsLiveData()

    }



    private fun preparePopItemRV() {
        binding.popRecview.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeMvvm.observePopularItemsLiveData().observe(
            viewLifecycleOwner
        ) { mealPopList: List<Meals> ->
            popItemsAdapter.setPopMeals(mealsPopList = mealPopList as ArrayList<Meals>)

        }
    }

    private fun onRandomReceitaClick() {
        binding.cardview.setOnClickListener {
            val intent = Intent(activity, ReceitaActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {

        homeMvvm.observeRandomReceitaLiveData().observe(
            viewLifecycleOwner,
        ) {
            homeMvvm.observeRandomReceitaLiveData().observe(
                viewLifecycleOwner,
            ) { mealList ->
                Glide.with(this@HomeFragment)
                    .load(mealList.strMealThumb)
                    .into(binding.cardimg)

                this.randomMeal = mealList
            }
        }

    }

}