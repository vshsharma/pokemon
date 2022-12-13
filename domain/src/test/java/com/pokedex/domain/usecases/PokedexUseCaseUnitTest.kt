package com.pokedex.domain.usecases

import com.pokedex.domain.MockResponse.error_message
import com.pokedex.domain.MockResponse.getDetailSuccessResult
import com.pokedex.domain.MockResponse.getPokemonAdditionalSuccessResult
import com.pokedex.domain.MockResponse.getSuccessResult
import com.pokedex.domain.common.Result
import com.pokedex.domain.repositories.PokedexRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PokedexUseCaseUnitTest {
    @MockK
    private val pokedexRepository = mockk<PokedexRepository>(relaxed = true)

    private lateinit var pokedexUseCase: PokedexUseCase

    @Before
    fun setUp() {
        pokedexUseCase = PokedexUseCase(
            pokedexRepository
        )
    }

    @Test
    fun `GIVEN GetPokedexList WHEN called THEN should return list of Pokemons`() = runTest {
        coEvery { pokedexRepository.getPokemonList() } returns getSuccessResult()
        when (val pokedexResult = pokedexUseCase.invoke()) {
            is Result.Success -> {
                Assert.assertEquals(pokedexResult.data?.count, 122)
            }
            else -> {
                Assert.assertTrue("error_message", true)
            }
        }
    }

    @Test
    fun `GIVEN GetPokedexDetail WHEN called THEN should return list of Pokemons detail`() = runTest {
        coEvery { pokedexRepository.getPokemonDetail("6") } returns getDetailSuccessResult()
        when (val pokedexDetailResult = pokedexUseCase.invokePokemonDetail("6")) {
            is Result.Success -> {
                Assert.assertEquals(pokedexDetailResult.data?.height, 344)
            }
            else -> {
                Assert.assertTrue(error_message, true)
            }
        }
    }

    @Test
    fun `GIVEN GetPokedexAdditionalInfo WHEN called THEN should return list of Pokemons detail`() = runTest {
        coEvery { pokedexRepository.getPokemonAdditionalDetail("6") } returns getPokemonAdditionalSuccessResult()
        when (val pokemonAdditionalDetailResult = pokedexUseCase.invokePokemonAdditionalDetail("6")) {
            is Result.Success -> {
                Assert.assertEquals(pokemonAdditionalDetailResult.data?.flavorTextEntries, "this is pokedex")
            }
            else -> {
                Assert.assertTrue(error_message, true)
            }
        }
    }
}