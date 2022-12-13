package com.pokedex.domain.repositories

import com.pokedex.domain.MockResponse.error_message
import com.pokedex.domain.MockResponse.getDetailSuccessResult
import com.pokedex.domain.MockResponse.getPokemonAdditionalSuccessResult
import com.pokedex.domain.MockResponse.getSuccessResult
import com.pokedex.domain.common.Result
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class PokedexRepositoryUnitTest {
    @MockK
    private val pokedexRepository = mockk<PokedexRepository>(relaxed = true)

    @Test
    fun `GIVEN GetPokedex list WHEN called THEN should return pokemon list`() = runTest {
        coEvery { pokedexRepository.getPokemonList() } returns getSuccessResult()
        when (val pokemonListResult = pokedexRepository.getPokemonList()) {
            is Result.Success -> {
                Assert.assertEquals(pokemonListResult.data?.count, 122)
            }
            else -> {
                Assert.assertTrue(error_message, true)
            }
        }
    }

    @Test
    fun `GIVEN GetPokedexDetail list WHEN called THEN should return pokemon detail`() = runTest {
        coEvery { pokedexRepository.getPokemonDetail("6") } returns getDetailSuccessResult()
        when (val pokemonDetailData = getDetailSuccessResult()) {
            is Result.Success -> {
                Assert.assertEquals(pokemonDetailData.data?.height, 344)
            }
            else -> {
                Assert.assertTrue(error_message, true)
            }
        }

        @Test
        fun `GIVEN GetPokedexAdditionalInfo WHEN called THEN should return list of Pokemons detail`() = runTest {
            coEvery { pokedexRepository.getPokemonAdditionalDetail("6") } returns getPokemonAdditionalSuccessResult()
            when (val pokemonAdditionalDetailResult = pokedexRepository.getPokemonAdditionalDetail("6")) {
                is Result.Success -> {
                    Assert.assertEquals(pokemonAdditionalDetailResult.data?.flavorTextEntries, "this is pokedex")
                }
                else -> {
                    Assert.assertTrue(error_message, true)
                }
            }
        }
    }
}