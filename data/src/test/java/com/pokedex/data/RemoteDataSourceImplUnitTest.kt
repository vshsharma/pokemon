package com.pokedex.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.pokedex.data.api.PokedexAPI
import com.pokedex.data.error.GeneralErrorHandlerImpl
import com.pokedex.data.mappers.*
import com.pokedex.data.repositories.RemoteDataSource
import com.pokedex.data.repositories.RemoteDataSourceImpl
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
class RemoteDataSourceImplUnitTest {

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
    fun `GIVEN GetPokemonList WHEN called THEN should return pokemon list`() = runTest{
        val jsonString = MockResponses.pokemonList
        server.enqueue(MockResponse().setResponseCode(200).setBody(jsonString))
        val resp = repository.getPokemonList()
        Assert.assertEquals(resp.data?.count, 1154)
    }

}