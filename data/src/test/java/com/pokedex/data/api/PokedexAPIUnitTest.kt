package com.pokedex.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.pokedex.data.MockResponses.additionalInfo
import com.pokedex.data.MockResponses.evolutionData
import com.pokedex.data.MockResponses.genderFilter
import com.pokedex.data.MockResponses.pokemonDetail
import com.pokedex.data.MockResponses.pokemonList
import com.pokedex.data.MockResponses.strengthWeakness
import com.pokedex.data.MockResponses.typeFilter
import com.pokedex.data.error.GeneralErrorHandlerImpl
import com.pokedex.data.mappers.*
import com.pokedex.data.repositories.RemoteDataSource
import com.pokedex.data.repositories.RemoteDataSourceImpl
import com.pokedex.data.util.Constants
import com.pokedex.domain.common.ErrorEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PokedexAPIUnitTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val server = MockWebServer()
    private var mapper = PokemonListEntityMapper()
    private val detailMapper = PokemonDetailEntityMapper()
    private val additionalInfoEntityMapper = PokemonAdditionalInfoEntityMapper()
    private val strengthWeaknessMapper = StrengthWeaknessMapper()
    private val typeFilterEntiryMapper = TypeFilterEntiryMapper()
    private val genderFilterEntityMapper = GenderFilterEntityMapper()
    private val evolutionChainEntityMapper = EvolutionChainEntityMapper()
    private var errorHandler = GeneralErrorHandlerImpl()
    private var dispatcher = Dispatchers.IO

    lateinit var okHttpClient: OkHttpClient
    lateinit var service: PokedexAPI
    lateinit var repository: RemoteDataSource

    @Before
    fun init() {
        server.start(8000)
        val BASE_URL = server.url("/").toString()
        okHttpClient = OkHttpClient
            .Builder()
            .build()
        service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(PokedexAPI::class.java)
        repository = RemoteDataSourceImpl(
            service,
            dispatcher,
            mapper,
            detailMapper,
            additionalInfoEntityMapper,
            strengthWeaknessMapper,
            typeFilterEntiryMapper,
            genderFilterEntityMapper,
            evolutionChainEntityMapper,
            errorHandler
        )
    }

    @After
    fun teardown() {
        server.shutdown()
    }

    @Test
    fun `GIVEN GetPokemonList WHEN called THEN should return pokemon list`() = runTest {
        val jsonString = pokemonList
        server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
        val resp = repository.getPokemonList()
        Assert.assertEquals(resp.data?.count, 1154)
    }

    @Test
    fun `GIVEN GetPokemonDetail WHEN called THEN should return pokemon detail data`() = runTest {
        val jsonString = pokemonDetail
        server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
        val resp = repository.getPokemonDetail("6")
        Assert.assertEquals(resp.data?.height, 17)
        Assert.assertEquals(resp.data?.id, 6)
    }

    @Test
    fun `GIVEN GetPokemonAdditionalData WHEN called THEN should return pokemon additional data`() =
        runTest {
            val jsonString = additionalInfo
            server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
            val resp = repository.getPokemonAdditionlDetail("6")
            Assert.assertEquals(resp.data?.id, 6)
        }

    @Test
    fun `GIVEN GetStrengthWeakness WHEN called THEN should return pokemon strength and weakness data`() =
        runTest {
            val jsonString = strengthWeakness
            server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
            val resp = repository.getStrengthWeakness("2")
            Assert.assertEquals(resp.data?.name, "rock")
        }

    @Test
    fun `GIVEN GetTypeFilter WHEN called THEN should return filter data for types`() = runTest {
        val jsonString = typeFilter
        server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
        val resp = repository.getTypeFilterData()
        Assert.assertEquals(resp.data?.typeFilters?.size, 20)
    }

    @Test
    fun `GIVEN GetGenderFilter WHEN called THEN should return filter data for gender`() = runTest {
        val jsonString = genderFilter
        server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
        val resp = repository.getGenderFilterData()
        Assert.assertEquals(resp.data?.genderResult?.size, 3)
    }

    @Test
    fun `GIVEN GetEvolutionData WHEN called THEN should return data for evolution of pokemon`() =
        runTest {
            val jsonString = evolutionData
            server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
            val resp = repository.getEvolutionChainData("2")
            Assert.assertEquals(resp.data?.species?.size, 3)
        }

    // Error test cases
    @Test
    fun `GIVEN GetPokemonList WHEN called THEN should return pokemon list error not found`() =
        runTest {
            val jsonString = "pokemonList"
            server.enqueue(MockResponse().setResponseCode(400).setBody(jsonString))
            val resp = repository.getPokemonList()
            Assert.assertEquals(resp.message, Constants.NOT_FOUND)
        }

    @Test
    fun `GIVEN  exception WHEN called THEN should return pokemon list error not found`() =
        runTest {
            val jsonString = "pokemonList"
            server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
            val resp = repository.getPokemonList()
            Assert.assertNull(resp.data)
            Assert.assertNotNull(resp.message)
        }

    @Test
    fun `GIVEN GetPokemonDetailError WHEN called THEN should return pokemon detail data error`() =
        runTest {
            val jsonString = "pokemonDetail"
            server.enqueue(MockResponse().setResponseCode(400).setBody(jsonString))
            val resp = repository.getPokemonDetail("6")
            Assert.assertEquals(resp.message, Constants.NOT_FOUND)
        }

    @Test
    fun `GIVEN GetPokemonDetail exception WHEN called THEN should return pokemon detail data error`() =
        runTest {
            val jsonString = "pokemonDetail"
            server.enqueue(MockResponse().setResponseCode(400).setBody(jsonString))
            val resp = repository.getPokemonDetail("6")
            Assert.assertNull(resp.data)
            Assert.assertNotNull(resp.message)
        }

    @Test
    fun `GIVEN GetPokemonAdditionalDataError WHEN called THEN should return pokemon additional data error`() =
        runTest {
            val jsonString = "additionalInfo"
            server.enqueue(MockResponse().setResponseCode(400).setBody(jsonString))
            val resp = repository.getPokemonAdditionlDetail("6")
            Assert.assertEquals(resp.message, Constants.NOT_FOUND)
        }

    @Test
    fun `GIVEN GetPokemonAdditionalDataError exception WHEN called THEN should return pokemon additional data error`() =
        runTest {
            val jsonString = "additionalInfo"
            server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
            val resp = repository.getPokemonAdditionlDetail("6")
            Assert.assertNull(resp.data)
            Assert.assertNotNull(resp.message)
        }

    @Test
    fun `GIVEN GetStrengthWeaknessErro WHEN called THEN should return pokemon strength and weakness error `() =
        runTest {
            val jsonString = "strengthWeakness"
            server.enqueue(MockResponse().setResponseCode(400).setBody(jsonString))
            val resp = repository.getStrengthWeakness("2")
            Assert.assertEquals(resp.errorEntity, ErrorEntity.NotFound)
        }

    @Test
    fun `GIVEN GetStrengthWeaknessError exception WHEN called THEN should return pokemon strength and weakness error `() =
        runTest {
            val jsonString = "strengthWeakness"
            server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
            val resp = repository.getStrengthWeakness("2")
            Assert.assertNull(resp.data)
            Assert.assertNotNull(resp.message)
        }

    @Test
    fun `GIVEN GetGenderFilterError WHEN called THEN should return filter data for gender error`() =
        runTest {
            val jsonString = "genderFilter"
            server.enqueue(MockResponse().setResponseCode(400).setBody(jsonString))
            val resp = repository.getGenderFilterData()
            Assert.assertEquals(resp.errorEntity, ErrorEntity.NotFound)
        }

    @Test
    fun `GIVEN GetGenderFilterError exception WHEN called THEN should return filter data for gender error`() =
        runTest {
            val jsonString = "genderFilter"
            server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
            val resp = repository.getGenderFilterData()
            Assert.assertNull(resp.data)
            Assert.assertNotNull(resp.message)
        }

    @Test
    fun `GIVEN GetTypeFilterError WHEN called THEN should return filter data for types error`() =
        runTest {
            val jsonString = "typeFilter"
            server.enqueue(MockResponse().setResponseCode(400).setBody(jsonString))
            val resp = repository.getTypeFilterData()
            Assert.assertEquals(resp.errorEntity, ErrorEntity.NotFound)
        }

    @Test
    fun `GIVEN GetTypeFilterError exception WHEN called THEN should return filter data for types error`() =
        runTest {
            val jsonString = "typeFilter"
            server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
            val resp = repository.getTypeFilterData()
            Assert.assertNull(resp.data)
            Assert.assertNotNull(resp.message)
        }

    @Test
    fun `GIVEN GetEvolutionDataError WHEN called THEN should return data for evolution of pokemon error`() =
        runTest {
            val jsonString = "evolutionData"
            server.enqueue(MockResponse().setResponseCode(400).setBody(jsonString))
            val resp = repository.getEvolutionChainData("2")
            Assert.assertEquals(resp.message, Constants.NOT_FOUND)
        }

    @Test
    fun `GIVEN GetEvolutionDataError exception WHEN called THEN should return data for evolution of pokemon error`() =
        runTest {
            val jsonString = "evolutionData"
            server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
            val resp = repository.getEvolutionChainData("2")
            Assert.assertNull(resp.data)
            Assert.assertNotNull(resp.message)
        }

}