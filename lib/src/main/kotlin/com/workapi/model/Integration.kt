package com.workapi.model

import com.squareup.moshi.Json
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "integration")
data class Integration (
    @field:Json(name = "provider") var provider: String = "",
    @field:Json(name = "provider_id") var providerId: String? = null,
    @field:Json(name = "media_type") var mediaType: String = ""
) : Resource()
