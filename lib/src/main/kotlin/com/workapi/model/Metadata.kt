package com.workapi.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Metadata (
    @Json(name = "total_results") val totalResults: Int = 0,
    @Json(name = "next_page_token") val nextPageToken: String = ""
)
