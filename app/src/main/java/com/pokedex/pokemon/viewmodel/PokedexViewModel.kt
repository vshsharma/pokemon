package com.pokedex.pokemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokedex.data.entities.MasterDetail
import com.pokedex.domain.usecases.PokedexUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.pokedex.domain.common.Result
import com.pokedex.domain.entities.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.util.HashSet
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val pokedexUseCase: PokedexUseCase
) : ViewModel() {
    private var pokemonMutableLiveData = MutableLiveData<PokemonData?>()
    private var pokemonDetailMutableLiveData = MutableLiveData<PokemonDetailData?>()
    private var pokemonAdditionalDetailMutableLiveData =
        MutableLiveData<PokemonAdditionalInfoData?>()
    private var pokemonStrengthWeaknessMutableLiveData = MutableLiveData<StrengthWeaknessData?>()
    private var typeFilterMutableLiveData = MutableLiveData<TypeFilterData?>()
    private var genderFilterMutableLiveData = MutableLiveData<GenderFilterData?>()
    private var evolutionDataMutableLiveData = MutableLiveData<EvolutionChainData?>()
    private var receivedFilterDataMutableLiveData = MutableLiveData<Boolean>()
    var selectedPokedexMutableLiveData = MutableLiveData<String>()
    var genderLiveData = MutableLiveData<HashSet<String>>()
    var typeSelectLiveData = MutableLiveData<HashSet<String>>()
    var masterDetailsLiveData = MutableLiveData<HashMap<String, MasterDetail>>()
    var detailsData = HashMap<String, MasterDetail>()

    private val errorResult = "Something went wrong"

    val pokemonDetailData: LiveData<PokemonDetailData?>
        get() = pokemonDetailMutableLiveData

    val pokemonAdditionalDetailData: LiveData<PokemonAdditionalInfoData?>
        get() = pokemonAdditionalDetailMutableLiveData

    val pokemonStrengthWeaknessData: LiveData<StrengthWeaknessData?>
        get() = pokemonStrengthWeaknessMutableLiveData

    val typeFilterData: LiveData<TypeFilterData?>
        get() = typeFilterMutableLiveData

    val genderFilterData: LiveData<GenderFilterData?>
        get() = genderFilterMutableLiveData

    val selectedPokedex: LiveData<String>
        get() = selectedPokedexMutableLiveData

    val receivedFiltereData: LiveData<Boolean>
        get() = receivedFilterDataMutableLiveData

    val evolutionData: LiveData<EvolutionChainData?>
        get() = evolutionDataMutableLiveData

    fun resetData() {
        masterDetailsLiveData.value = detailsData
    }

    fun getPokemonList() {
        viewModelScope.launch {
            when (val pokemonListInfo = pokedexUseCase.invoke()) {
                is Result.Success -> {
                    val pokemonInfo = pokemonListInfo.data
                    pokemonInfo?.let {
                        pokemonMutableLiveData.value = pokemonInfo
                        getDetailsData(pokemonInfo)
                    }
                }
                is Result.Error -> {
                    updateErrorResult(errorResult)
                }
            }
        }
    }

    private suspend fun getHomeDetailData(name: String): Result<PokemonDetailData> {
        return pokedexUseCase.invokePokemonDetail(name)
    }

    private suspend fun getHomeAdditionlData(name: String): Result<PokemonAdditionalInfoData> {
        return pokedexUseCase.invokePokemonAdditionalDetail(name)
    }

    private suspend fun getHomeStrengthWeaknessData(name: String): Result<StrengthWeaknessData> {
        return pokedexUseCase.invokeStrengthWeaknessDetail(name)
    }

    /**
     * Method to call related API's to inflate data at pokedex list screen
     */
    private fun getDetailsData(pokemonInfo: PokemonData) {
        runBlocking {
            for (pokemon in pokemonInfo.results) {
                detailsData[pokemon.name!!] = MasterDetail()
                val homeDetail = async { getHomeDetailData(pokemon.name!!) }
                val homeAdditionalInfo = async { getHomeAdditionlData(pokemon.name!!) }
                val homeStrengthWeakness = async { getHomeStrengthWeaknessData(pokemon.name!!) }
                val latch = CountDownLatch(2)
                val detailData = homeDetail.await()
                val additionalData = homeAdditionalInfo.await()
                val strengthWeaknessData = homeStrengthWeakness.await()

                // strengthWeaknessData
                when (strengthWeaknessData) {
                    is Result.Success -> {
                        val pokemonStrengthWeaknessData = strengthWeaknessData.data
                        pokemonStrengthWeaknessData?.let {
                            val masterDetail = detailsData[pokemonStrengthWeaknessData.name!!]
                            masterDetail!!.strengthWeaknessData = pokemonStrengthWeaknessData
                            detailsData[pokemonStrengthWeaknessData.name!!] = masterDetail
                            latch.countDown()
                        }
                    }
                    is Result.Error -> {
                        latch.countDown()
                        //updateErrorResult(pokemonListInfo)
                    }
                }

                // Additional data
                when (additionalData) {
                    is Result.Success -> {
                        val pokemonAdditionalDetailInfo = additionalData.data
                        pokemonAdditionalDetailInfo?.let {
                            val masterDetail = detailsData[pokemonAdditionalDetailInfo.name!!]
                            masterDetail!!.pokemonAdditionalInfoData = pokemonAdditionalDetailInfo
                            detailsData[pokemonAdditionalDetailInfo.name!!] = masterDetail
                            latch.countDown()
                        }
                    }
                    is Result.Error -> {
                        //updateErrorResult(pokemonListInfo)
                        latch.countDown()
                    }
                }

                // Detail data
                when (detailData) {
                    is Result.Success -> {
                        val pokemonDetailInfo = detailData.data
                        pokemonDetailInfo?.let {
                            val masterDetail = detailsData[pokemonDetailInfo.name!!]
                            masterDetail!!.pokemonDetailData = pokemonDetailInfo
                            detailsData[pokemonDetailInfo.name!!] = masterDetail
                        }
                        latch.countDown()
                    }
                    is Result.Error -> {
                        latch.countDown()
                        //updateErrorResult(pokemonListInfo)
                    }
                }
            }
            masterDetailsLiveData.value = detailsData

        }
    }

    /**
     * Method to get the data required for detail fragment
     */
    fun getPokemonDetail(value: String?) {
        val dataKey = masterDetailsLiveData.value
        pokemonDetailMutableLiveData.value = dataKey?.get(value)?.pokemonDetailData
        pokemonAdditionalDetailMutableLiveData.value =
            dataKey?.get(value)?.pokemonAdditionalInfoData
        getPokemonStrengthWeaknessInfo(pokemonDetailMutableLiveData.value?.id.toString())

        getEvolutionChain(dataKey?.get(value)?.pokemonAdditionalInfoData?.evolutionUrl)
    }

    fun getEvolutionChain(evolutionUrl: String?) {
        val id = evolutionUrl?.replace("https://pokeapi.co/api/v2/evolution-chain/", "")
            ?.replace("/", "")
        viewModelScope.launch {
            when (val evolutionDataInfo =
                pokedexUseCase.invokeEvolutionData(id.toString())) {
                is Result.Success -> {
                    val evolutionDataInfoData = evolutionDataInfo.data
                    evolutionDataInfoData?.let {
                        evolutionDataMutableLiveData.value = evolutionDataInfoData
                    }
                }
                is Result.Error -> {
                    updateErrorResult(errorResult)
                }
            }
        }
    }

    /**
     * Get Combined data for filters
     */
    fun fetchFilterData() {
        runBlocking {
            val typeResponse = async { getFiltersData() }
            val genderResponse = async { getFilterGender() }

            val gender = genderResponse.await()
            val types = typeResponse.await()
            when (types) {
                is Result.Success -> {
                    val filteredTypeData = types.data
                    filteredTypeData?.let {
                        typeFilterMutableLiveData.value = it
                    }
                }
                is Result.Error -> {
                    updateErrorResult(errorResult)
                }
            }

            when (gender) {
                is Result.Success -> {
                    val filteredGenderData = gender.data
                    filteredGenderData?.let {
                        genderFilterMutableLiveData.value = it
                    }
                }
                is Result.Error -> {
                    updateErrorResult(errorResult)
                }
            }
            receivedFilterDataMutableLiveData.value = true
        }
    }

    /**
     * Invoke filter API for getting gender filter
     */
    private suspend fun getFilterGender(): Result<GenderFilterData> {
        return pokedexUseCase.invokeGenderFilterData()
    }

    /**
     * Invoke filter API for getting types filter
     */
    private suspend fun getFiltersData(): Result<TypeFilterData> {
        return pokedexUseCase.invokeTypeFilterData()
    }

    /**
     * Method to invoke API call to get the strength and weakness of pokedex
     */
    fun getPokemonStrengthWeaknessInfo(idPokedex: String) {
        viewModelScope.launch {
            when (val pokemonStrengthWeaknessInfo =
                pokedexUseCase.invokeStrengthWeaknessDetail(idPokedex)) {
                is Result.Success -> {
                    val pokemonStrengthWeaknessData = pokemonStrengthWeaknessInfo.data
                    pokemonStrengthWeaknessData?.let {
                        pokemonStrengthWeaknessMutableLiveData.value = pokemonStrengthWeaknessData
                    }
                }
                is Result.Error -> {
                    //updateErrorResult(pokemonListInfo)
                }
            }
        }
    }

    /**
     * updates the error result
     */
    private fun updateErrorResult(errorResult: String) {
        Timber.tag("Error").d(errorResult)
    }

    /**
     * Method to apply filter and get updated data
     */
    fun applyFilters() {
        if (typeSelectLiveData.value?.isEmpty() == true) {
            resetData()
            return
        }
        val filteredData = HashMap<String, MasterDetail>()
        detailsData.forEach { (key, value) ->
            // filter for
            val list = typeSelectLiveData.value?.toList() // filters list
            if (list != null) {
                for (string in list) {
                    val typesList =
                        value.pokemonDetailData?.types?.map { types: Types -> types.type?.name }
                    if ((typesList != null) && typesList.contains(string)) {
                        filteredData.put(key, value)
                    }
                }
            }
        }
        masterDetailsLiveData.value = filteredData
    }
}