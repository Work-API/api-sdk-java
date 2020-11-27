package com.workapi.model

import com.squareup.moshi.Json
import moe.banana.jsonapi2.JsonApi

@JsonApi(type = "calendar")
data class Calendar (
    @field:Json(name = "name") val name: String = ""
) : WorkApiResource()
