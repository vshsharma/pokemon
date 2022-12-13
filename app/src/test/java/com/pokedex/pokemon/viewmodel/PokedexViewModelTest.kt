package com.pokedex.pokemon.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pokedex.data.entities.MasterDetail
import com.pokedex.data.mappers.PokemonListEntityMapper
import com.pokedex.domain.usecases.PokedexUseCase
import com.pokedex.pokemon.MockResponse.getDetailSuccessResult
import com.pokedex.pokemon.MockResponse.getEvolutionData
import com.pokedex.pokemon.MockResponse.getGenderFilterData
import com.pokedex.pokemon.MockResponse.getPokemonAdditionalSuccessResult
import com.pokedex.pokemon.MockResponse.getStrengthWeaknessResult
import com.pokedex.pokemon.MockResponse.getSuccessResult
import com.pokedex.pokemon.MockResponse.getTypeFilterData
import com.pokedex.pokemon.TestCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class PokedexViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = TestCoroutineRule()

    private lateinit var pokedexViewModel: PokedexViewModel
    private val pokedexUseCase = mockk<PokedexUseCase>()
    private val pokemonListEntityMapper = PokemonListEntityMapper()
    private val mContextMock = mockk<Context>(relaxed = true)

    @Before
    fun setUp() {
        pokedexViewModel = PokedexViewModel(
            pokedexUseCase
        )
    }

    @Test
    fun `GIVEN pokemon list WHEN called THEN should return pokemon list response`() = runBlocking {
        coEvery { pokedexUseCase.invoke() } returns getSuccessResult()
        coEvery { pokedexUseCase.invokePokemonDetail("bulbasaur") } returns getDetailSuccessResult()
        coEvery { pokedexUseCase.invokePokemonAdditionalDetail("bulbasaur") } returns getPokemonAdditionalSuccessResult()
        coEvery { pokedexUseCase.invokeStrengthWeaknessDetail("bulbasaur") } returns getStrengthWeaknessResult()

        pokedexViewModel.getPokemonList()


        val result = pokedexViewModel.masterDetailsLiveData.value
        assertNotNull(result)
        assertEquals(result?.size, 1)
    }

    @Test
    fun `GIVEN pokemon detail data WHEN called THEN should return pokemon detail response`() = runBlocking {
        coEvery { pokedexUseCase.invoke() } returns getSuccessResult()
        coEvery { pokedexUseCase.invokePokemonDetail("bulbasaur") } returns getDetailSuccessResult()
        coEvery { pokedexUseCase.invokePokemonAdditionalDetail("bulbasaur") } returns getPokemonAdditionalSuccessResult()
        coEvery { pokedexUseCase.invokeStrengthWeaknessDetail("bulbasaur") } returns getStrengthWeaknessResult()
        pokedexViewModel.getPokemonList()
        pokedexViewModel.getPokemonDetail("bulbasaur")

        val resultDetail = pokedexViewModel.pokemonDetailData.value
        assertNotNull(resultDetail)
        assertEquals(resultDetail?.name, "bulbasaur")

        val result = pokedexViewModel.pokemonAdditionalDetailData.value
        assertNotNull(result)
        assertEquals(result?.name, "bulbasaur")
    }

    @Test
    fun `GIVEN pokemon evolution data WHEN called THEN should return pokemon evolution response`() = runBlocking {
        coEvery { pokedexUseCase.invokeEvolutionData("1") } returns getEvolutionData()

        pokedexViewModel.getEvolutionChain("https://pokeapi.co/api/v2/evolution-chain/1")
        val resultDetail = pokedexViewModel.evolutionData.value
        assertNotNull(resultDetail?.species)
    }

    @Test
    fun `GIVEN pokemon filters data WHEN called THEN should return pokemon types and gender available response`() = runBlocking {
        coEvery { pokedexUseCase.invokeTypeFilterData() } returns getTypeFilterData()
        coEvery { pokedexUseCase.invokeGenderFilterData() } returns getGenderFilterData()

        pokedexViewModel.fetchFilterData()
        val resultDetail = pokedexViewModel.typeFilterData.value
        assertNotNull(resultDetail?.typeFilters)

        val resultDetailGender = pokedexViewModel.genderFilterData.value
        assertNotNull(resultDetailGender?.genderResult)
    }

}