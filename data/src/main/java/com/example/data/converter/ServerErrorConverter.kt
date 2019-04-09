package com.example.data.converter

import com.example.data.entity.login.LoginResponse
import com.example.data.entity.login.LoginResponseObject
import com.example.domain.utils.exceptions.MessageServerException
import com.example.domain.utils.exceptions.ServerException
import com.example.domain.utils.exceptions.UnauthorizedServerException
import com.example.domain.utils.exceptions.UnknownServerException
import com.google.gson.GsonBuilder
import okhttp3.Response
import timber.log.Timber

object ServerErrorConverter {

    private const val TAG = "ServerErrorConverter"

    fun fromNetwork(response: Response): ServerException {
        val httpCode = response.code()

        val responseContent = try {
            response.body()!!.string()
        } catch (e: Exception) {
            response.message()
        }
        
        var serverErrorResponseObject: String? = null
        var serverErrorMessage: String? = null

        try {
            val gson = GsonBuilder().create()
            val errorResponse = gson.fromJson(responseContent, LoginResponse::class.java)

            serverErrorResponseObject = errorResponse.responseObject.toString()
            serverErrorMessage = errorResponse.message
        } catch (e: Exception) {
            Timber.tag(TAG).e("httpStatusCode=$httpCode, error=$responseContent $e")
        }
        
        return when {
            httpCode == 401 -> UnauthorizedServerException(
                serverErrorResponseObject,
                serverErrorMessage
            )
            serverErrorResponseObject == null || serverErrorMessage == null -> UnknownServerException()
            else -> MessageServerException(
                serverErrorResponseObject,
                serverErrorMessage
            )
        }
    }
}