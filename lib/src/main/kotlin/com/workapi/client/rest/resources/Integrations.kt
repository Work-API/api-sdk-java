package com.workapi.client.rest.resources

import com.workapi.serializers.IntegrationSerializer
import com.workapi.model.Integration
import com.workapi.client.rest.WorkApiClient
import com.mashape.unirest.http.HttpResponse
import org.json.JSONObject

class Integrations(client: WorkApiClient): BaseResource(client) {
    private val integrationAdapter = IntegrationSerializer()

    fun list(): List<Integration> {
        val response: HttpResponse<String> = makeRetryableRequest({ client.httpGet("/integrations")!! })
        return integrationAdapter.deserializeIntegrations(response.body)
    }

    fun fetch(integrationId: String): Integration {
        val response = makeRetryableRequest({ client.httpGet("/integrations/$integrationId")!! })
        return integrationAdapter.deserializeIntegration(response.body).get()
    }

    fun create(integration: Integration): Integration {
        val serializedIntegration = integrationAdapter.serializeIntegration(integration)
        val response = client.httpPost("/integrations", serializedIntegration)

        return integrationAdapter.deserializeIntegration(response.body).get()
    }

    fun update(integration: Integration): Integration {
        val serializedIntegration = integrationAdapter.serializeIntegration(integration)
        val response = client.httpPut("/integrations/${integration.id}", serializedIntegration)

        return integrationAdapter.deserializeIntegration(response.body).get()
    }

    fun delete(integrationId: String) {
        client.httpDelete("/integrations/${integrationId}")
    }

    fun auth(integrationId: String) : String {
        val response = client.httpGet("/auth/init/$integrationId")
        val jsonResponse = JSONObject(response!!.body)
        return jsonResponse["uri"] as String
    }
}

