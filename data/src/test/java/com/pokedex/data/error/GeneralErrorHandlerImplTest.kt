package com.pokedex.data.error

import com.pokedex.domain.common.ErrorEntity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class GeneralErrorHandlerImplTest {

    @Test
    fun `Test the general error handler for different use cases`() {
        var errorEntity = GeneralErrorHandlerImpl().getError(Throwable(IOException()))
        Assert.assertEquals(errorEntity, ErrorEntity.Unknown)
    }

    @Test
    fun `Test the general error handler for different network error`() {
        var errorEntity = GeneralErrorHandlerImpl().getError(IOException())
        Assert.assertEquals(errorEntity, ErrorEntity.Network)
    }

    @Test
    fun `Test the general error handler for different HTTP status error`() {
        var errorEntity = GeneralErrorHandlerImpl().
        getError(HttpException(Response.error<ResponseBody>(404 ,
            "some content".toResponseBody("plain/text".toMediaTypeOrNull())
        ))
        )
        Assert.assertEquals(errorEntity, ErrorEntity.NotFound)
    }

    @Test
    fun `Test the general error handler for different HTTP Access denied error`() {
        var errorEntity = GeneralErrorHandlerImpl().
        getError(HttpException(Response.error<ResponseBody>(403 ,
            "some content".toResponseBody("plain/text".toMediaTypeOrNull())
        ))
        )
        Assert.assertEquals(errorEntity, ErrorEntity.AccessDenied)
    }

    @Test
    fun `Test the general error handler for different HTTP service unavailable error`() {
        var errorEntity = GeneralErrorHandlerImpl().
        getError(HttpException(Response.error<ResponseBody>(503 ,
            "some content".toResponseBody("plain/text".toMediaTypeOrNull())
        ))
        )
        Assert.assertEquals(errorEntity, ErrorEntity.ServiceUnavailable)
    }

    @Test
    fun `Test the general error handler for different HTTP service other error error`() {
        var errorEntity = GeneralErrorHandlerImpl().
        getError(HttpException(Response.error<ResponseBody>(502 ,
            "some content".toResponseBody("plain/text".toMediaTypeOrNull())
        ))
        )
        Assert.assertEquals(errorEntity, ErrorEntity.Network)
    }
}