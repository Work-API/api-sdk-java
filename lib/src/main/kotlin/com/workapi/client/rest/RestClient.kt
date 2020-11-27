package com.workapi.client.rest 

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import com.mashape.unirest.request.HttpRequest
import org.json.JSONException
import java.util.*

abstract class RestClient(
    open protected val token: String?,
    protected val url: String,
    private val requestContentType: String = "application/json"
) {
    fun httpGet(path: String, queryParams: Array<String>? = null): HttpResponse<String>? {
        val target = StringBuilder()
        target.append(url)
        target.append(path)

        if (queryParams != null && queryParams.isNotEmpty()) {
            target.append(queryParams.joinToString(separator = "&", prefix = "?"))
        }

        return try {
            val request = Unirest.get(target.toString())
            applyHeaders(request)

            val response = request.asString()
            verifyResponseSuccess(response.code, response.body?.toString() ?: "")

            return response
        } catch (e: UnirestException) {
            e.printStackTrace()
            null
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }
    }

    fun httpPost(path: String, body: String?, withAuth: Boolean = true): HttpResponse<String> {
        val request = Unirest.post("$url$path")
        applyHeaders(request, withAuth)

        val response = request.body(body).asString()
        verifyResponseSuccess(response.code, response.body.toString())

        return response
    }

    fun httpPut(path: String, body: String): HttpResponse<String> {
        val request = Unirest.put("$url$path")
        applyHeaders(request)

        val response = request.body(body).asString()
        verifyResponseSuccess(response.code, response.body.toString())

        return response
    }

    fun httpDelete(path: String) : HttpResponse<String>? {
        val request = Unirest.delete("$url$path")
        applyHeaders(request)

        val response = request.asString()
        verifyResponseSuccess(response.code, response.body?.toString())

        return response
    }

    private fun applyHeaders(request: HttpRequest, withAuth: Boolean = true) {
        request
            .header("Content-Type", requestContentType)

        if (withAuth) {
            request.header("Authorization", "Bearer ${authorizationHeaderValue()}")
        }

        applyCustomHeaders(request)
    }

    protected open fun authorizationHeaderValue() : String? {
        return token ?: throw Exception("Authorization header not found")
    }

    protected open fun applyCustomHeaders(request: HttpRequest) { }

    @Throws(Exception::class)
    private fun verifyResponseSuccess(code : Int, message: String?) {
        if (code >= 400) {
            throw Exception("Request error: $message")
        }
    }
}
