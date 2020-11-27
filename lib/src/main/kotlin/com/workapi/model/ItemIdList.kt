package com.workapi.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemIdList(
    @Json(name = "emails") val emails : List<String>? = emptyList(),
    @Json(name = "events") val events : List<String>? = emptyList()
)
