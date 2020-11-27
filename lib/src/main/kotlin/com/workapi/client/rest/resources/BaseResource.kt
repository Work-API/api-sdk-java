package com.workapi.client.rest.resources

import com.workapi.client.rest.Response
import com.workapi.client.rest.WorkApiClient
import com.workapi.serializers.BaseSerializer
import com.mashape.unirest.http.HttpResponse
import java.util.concurrent.TimeUnit

open class BaseResource(protected val client: WorkApiClient) {
    private lateinit var serializer : BaseSerializer

    protected fun buildArrayQueryParam(key: String, items: List<String>) : String{
        return items.joinToString(separator = "&") { "$key[]=$it" }
    }

    protected fun makeRetryableRequest(call: () -> HttpResponse<String>, attempts: Int = 3): HttpResponse<String> = try {
        call()
    } catch(exception: Exception) {
        if (attempts == 0) {
            throw exception
        } else {
            makeRetryableRequest(call, attempts = attempts - 1)
        }
    }

    companion object {
        internal const val TAG: String = "BaseResource"
    }
}
