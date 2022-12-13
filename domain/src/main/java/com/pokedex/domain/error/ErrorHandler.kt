package com.pokedex.domain.error

import com.pokedex.domain.common.ErrorEntity

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}