package com.marcos.ktlproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.marcos.ktlproject.data.pojo.ReceitaDetail
import com.marcos.ktlproject.databinding.FragmentHomeBinding
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
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomReceitaLiveData().observe(viewLifecycleOwner,object : Observer<ReceitaDetail>{
            override fun onChanged(value: ReceitaDetail) {
                Glide.with(this@HomeFragment)
                    .load(value.strMealThumb)
                    .into(binding.cardimg)
            }
        })

    }

}

