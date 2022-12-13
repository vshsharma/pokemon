package com.pokedex.data.repositories

import com.pokedex.data.api.PokedexAPI
import com.pokedex.data.di.IoDispatcher
import com.pokedex.data.entities.*
import com.pokedex.data.mappers.*
import com.pokedex.data.util.Constants
import com.pokedex.data.util.Constants.SERVICE_UNAVAILABLE_CODE
import com.pokedex.domain.common.ErrorEntity
import com.pokedex.domain.error.ErrorHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.pokedex.domain.common.Result
import com.pokedex.domain.entities.*
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val service: PokedexAPI,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val mapper: PokemonListEntityMapper,
    private val detailMapper: PokemonDetailEntityMapper,
    private val additionalInfoEntityMapper: PokemonAdditionalInfoEntityMapper,
    private val strengthWeaknessMapper: StrengthWeaknessMapper,
    private val typeFilterEntiryMapper: TypeFilterEntiryMapper,
    private val genderFilterEntityMapper: GenderFilterEntityMapper,
    private val evolutionChainEntityMapper: EvolutionChainEntityMapper,
    private val errorHandler: ErrorHandler,
) : RemoteDataSource {

    override suspend fun getPokemonList(): Result<PokemonData> =
        withContext(dispatcher) {
            try {
                val response: Response<PokedexApiResponse> = service.getPokemonList()
                val pokedexApiResponse = response.body()
                pokedexApiResponse?.let {
                    val dataLayerResponse = mapper.mapToDomainLayer(pokedexApiResponse)
                    if (response.isSuccessful) {
                        return@withContext Result.Success(dataLayerResponse)
                    } else {
                        return@withContext getWrongInputResult()
                    }
                } ?: run {
                    return@withContext getErrorResult(response)
                }
            } catch (e: Exception) {
                return@withContext getExceptionResult(e)
            }
        }

    override suspend fun getPokemonDetail(pokemonId: String): Result<PokemonDetailData> =
        withContext(dispatcher) {
            try {
                val response: Response<PokedexDetailApiResponse> =
                    service.getPokemonDetail(pokemonId)
                val pokedexDetailApiResponse = response.body()
                pokedexDetailApiResponse?.let {
                    val dataLayerResponse = detailMapper.mapToDomainLayer(pokedexDetailApiResponse)
                    if (response.isSuccessful) {
                        return@withContext Result.Success(dataLayerResponse)
                    } else {
                        return@withContext Result.Error(
                            message = Constants.WRONG_INPUT,
                            errorEntity = ErrorEntity.Unknown
                        )
                    }
                } ?: run {
                    return@withContext Result.Error(
                        message = Constants.NOT_FOUND,
                        errorEntity = ErrorEntity.NotFound
                    )
                }
            } catch (e: Exception) {
                return@withContext Result.Error(
                    message = e.localizedMessage ?: Constants.UNKNOWN_ERROR,
                    errorEntity = errorHandler.getError(e)
                )
            }
        }

    override suspend fun getPokemonAdditionlDetail(pokemonId: String): Result<PokemonAdditionalInfoData> =
        withContext(dispatcher) {
            try {
                val response: Response<PokemonAdditionalInfo> =
                    service.getPokemonAdditionalDetail(pokemonId)
                val pokemonAdditionalInfo = response.body()
                pokemonAdditionalInfo?.let {
                    val dataLayerResponse = additionalInfoEntityMapper.mapToDomainLayer(pokemonAdditionalInfo)
                    if (response.isSuccessful) {
                        return@withContext Result.Success(dataLayerResponse)
                    } else {
                        return@withContext Result.Error(
                            message = Constants.WRONG_INPUT,
                            errorEntity = ErrorEntity.Unknown
                        )
                    }
                } ?: run {
                    return@withContext Result.Error(
                        message = Constants.NOT_FOUND,
                        errorEntity = ErrorEntity.NotFound
                    )
                }
            } catch (e: Exception) {
                return@withContext Result.Error(
                    message = e.localizedMessage ?: Constants.UNKNOWN_ERROR,
                    errorEntity = errorHandler.getError(e)
                )
            }
        }

    override suspend fun getStrengthWeakness(pokemonId: String): Result<StrengthWeaknessData> =
        withContext(dispatcher) {
            try {
                val response: Response<StrengthWeaknessAPIResponse> =
                    service.getStrengthWeakness(pokemonId)
                val pokemonStrengthWeaknessInfo = response.body()
                pokemonStrengthWeaknessInfo?.let {
                    val dataLayerResponse = strengthWeaknessMapper.mapToDomainLayer(pokemonStrengthWeaknessInfo)
                    if (response.isSuccessful) {
                        return@withContext Result.Success(dataLayerResponse)
                    } else {
                        return@withContext Result.Error(
                            message = Constants.WRONG_INPUT,
                            errorEntity = ErrorEntity.Unknown
                        )
                    }
                } ?: run {
                    return@withContext Result.Error(
                        message = Constants.NOT_FOUND,
                        errorEntity = ErrorEntity.NotFound
                    )
                }
            } catch (e: Exception) {
                return@withContext Result.Error(
                    message = e.localizedMessage ?: Constants.UNKNOWN_ERROR,
                    errorEntity = errorHandler.getError(e)
                )
            }
        }

    override suspend fun getTypeFilterData(): Result<TypeFilterData> =
        withContext(dispatcher) {
            try {
                val response: Response<TypeFilterApiResponse> =
                    service.getTypeFilter()
                val typeFilterDataInfo = response.body()
                typeFilterDataInfo?.let {
                    val dataLayerResponse =
                        typeFilterEntiryMapper.mapToDomainLayer(typeFilterDataInfo)
                    if (response.isSuccessful) {
                        return@withContext Result.Success(dataLayerResponse)
                    } else {
                        return@withContext Result.Error(
                            message = Constants.WRONG_INPUT,
                            errorEntity = ErrorEntity.Unknown
                        )
                    }
                } ?: run {
                    return@withContext Result.Error(
                        message = Constants.NOT_FOUND,
                        errorEntity = ErrorEntity.NotFound
                    )
                }
            } catch (e: Exception) {
                return@withContext Result.Error(
                    message = e.localizedMessage ?: Constants.UNKNOWN_ERROR,
                    errorEntity = errorHandler.getError(e)
                )
            }
        }

    override suspend fun getGenderFilterData(): Result<GenderFilterData> =
        withContext(dispatcher) {
            try {
                val response: Response<GenderFilterApiResponse> =
                    service.getGenderFilter()
                val genderFilterInfo = response.body()
                genderFilterInfo?.let {
                    val dataLayerResponse =
                        genderFilterEntityMapper.mapToDomainLayer(genderFilterInfo)
                    if (response.isSuccessful) {
                        return@withContext Result.Success(dataLayerResponse)
                    } else {
                        return@withContext Result.Error(
                            message = Constants.WRONG_INPUT,
                            errorEntity = ErrorEntity.Unknown
                        )
                    }
                } ?: run {
                    return@withContext Result.Error(
                        message = Constants.NOT_FOUND,
                        errorEntity = ErrorEntity.NotFound
                    )
                }
            } catch (e: Exception) {
                return@withContext Result.Error(
                    message = e.localizedMessage ?: Constants.UNKNOWN_ERROR,
                    errorEntity = errorHandler.getError(e)
                )
            }
    }

    override suspend fun getEvolutionChainData(id: String): Result<EvolutionChainData> =
        withContext(dispatcher) {
            try {
                val response: Response<EvolutionChainApiResponse> =
                    service.getEvolutionChainData(id)
                val evolutionChainInfo = response.body()
                evolutionChainInfo?.let {
                    val dataLayerResponse =
                        evolutionChainEntityMapper.mapToDomainLayer(evolutionChainInfo)
                    if (response.isSuccessful) {
                        return@withContext Result.Success(dataLayerResponse)
                    } else {
                        return@withContext Result.Error(
                            message = Constants.WRONG_INPUT,
                            errorEntity = ErrorEntity.Unknown
                        )
                    }
                } ?: run {
                    return@withContext Result.Error(
                        message = Constants.NOT_FOUND,
                        errorEntity = ErrorEntity.NotFound
                    )
                }
            } catch (e: Exception) {
                return@withContext Result.Error(
                    message = e.localizedMessage ?: Constants.UNKNOWN_ERROR,
                    errorEntity = errorHandler.getError(e)
                )
            }
    }


    /**
     * gets the exception Error result
     */
    private fun getExceptionResult(e: Exception): Result.Error<PokemonData> {
        return Result.Error(
            message = e.localizedMessage ?: Constants.UNKNOWN_ERROR,
            errorEntity = errorHandler.getError(e)
        )
    }

    /**
     * gets the wrong Input Error Result
     */
    private fun getWrongInputResult(): Result.Error<PokemonData> {
        return Result.Error(
            message = Constants.WRONG_INPUT,
            errorEntity = ErrorEntity.Unknown
        )
    }

    /**
     * Gets the error Result
     */
    private fun getErrorResult(response: Response<PokedexApiResponse>): Result.Error<PokemonData> {
        return when (response.raw().code) {
            SERVICE_UNAVAILABLE_CODE -> Result.Error(
                message = Constants.SERVICE_UNAVAILABLE,
                errorEntity = ErrorEntity.ServiceUnavailable
            )
            else -> {
                Result.Error(
                    message = Constants.NOT_FOUND,
                    errorEntity = ErrorEntity.NotFound
                )
            }
        }
    }
}