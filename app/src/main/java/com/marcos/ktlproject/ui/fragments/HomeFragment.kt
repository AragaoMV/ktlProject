package com.marcos.ktlproject.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.marcos.ktlproject.data.pojo.Meals
import com.marcos.ktlproject.databinding.FragmentHomeBinding
import com.marcos.ktlproject.ui.activities.ReceitaActivity
import com.marcos.ktlproject.viewModel.HomeViewModel



class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMvvm.getRandomReceita()
        observeRandomMeal()
        onRandomReceitaClick()
    }

    private fun onRandomReceitaClick() {
        binding.cardview.setOnClickListener{
            val intent = Intent(activity, ReceitaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {

        homeMvvm.observeRandomReceitaLiveData().observe(viewLifecycleOwner, { mealList ->
            Glide.with(this@HomeFragment)
                .load(mealList.get(0).strMealThumb)
                .into(binding.cardimg)
        })
    }

}

