package com.workapi.client.rest

import com.workapi.client.rest.resources.Emails
import com.workapi.client.rest.resources.Events
import com.workapi.client.rest.resources.Integrations
import com.workapi.client.rest.resources.Users
import com.mashape.unirest.request.HttpRequest

class WorkApiClient @JvmOverloads constructor(
    override var token : String?,
    url: String = "https://api.work-api.com"
) : RestClient(
    token = token,
    url = url,
    requestContentType = "application/vnd.api+json"
) {
    @JvmField var users: Users = Users(this)
    @JvmField var integrations: Integrations = Integrations(this)
    @JvmField var emails: Emails = Emails(this)
    @JvmField var events: Events = Events(this)

    fun refreshToken() {
        val user = users.fetch()
        token = user.token
    }
}
