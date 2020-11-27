package com.workapi.model

import com.squareup.moshi.Json
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "user")
data class User (
    @field:Json(name = "arbitrary_id") var arbitraryId: String = "",
    @field:Json(name = "token") var token: String = ""
) : Resource()
