package com.pokedex.pokemon.core

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.pokedex.pokemon.PokedexApplication

// A custom runner to set up the instrumented application class for tests.
class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, PokedexApplication::class.java.name, context)
    }
}