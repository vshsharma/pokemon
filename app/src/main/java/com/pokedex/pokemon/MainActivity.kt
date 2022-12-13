package com.pokedex.pokemon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pokedex.pokemon.databinding.ActivityMainBinding
import com.pokedex.pokemon.ui.fragment.PokedexListFragment
import com.pokedex.pokemon.viewmodel.PokedexViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: PokedexViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launchFragment()

        viewModel = ViewModelProvider(this)[PokedexViewModel::class.java]
        viewModel.getPokemonList()

        AnalyticsHelper.logScreenLoadEvent("MainActivity")
    }

    private fun launchFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(binding.frameContainer.id, PokedexListFragment())
        transaction.commit()
    }
}