package com.workapi.client.rest.resources

import com.workapi.client.rest.WorkApiClient
import com.workapi.model.User
import com.workapi.serializers.UserSerializer

class Users(client: WorkApiClient): BaseResource(client) {
    private val userAdapter = UserSerializer()

    fun fetch(): User {
        val response = makeRetryableRequest({ client.httpGet("/users/me")!! })
        return userAdapter.deserializeUser(response.body).get()
    }
}
