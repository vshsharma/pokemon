package com.pokedex.pokemon.util

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pokedex.pokemon.R
import io.mockk.mockk
import org.junit.Assert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class UtilsTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val mContextMock = mockk<Context>(relaxed = true)

    @Test
    fun getGradient() {
        val colorsArray: MutableList<Int> =
            mutableListOf(mContextMock.resources.getColor(R.color.fire, null),
                mContextMock.resources.getColor(R.color.grass, null),
                mContextMock.resources.getColor(R.color.flying, null))
        val drawable = Utils().getGradient(colorsArray)
        assertNotNull(drawable)
    }

    @Test
    fun getGradientColors() {
        val type = mutableListOf<String?>("fire", "grass", "flying")
        val gradientDrawable = Utils().getGradientColors(mContextMock, types = type)
        assertNotNull(gradientDrawable)
    }
}