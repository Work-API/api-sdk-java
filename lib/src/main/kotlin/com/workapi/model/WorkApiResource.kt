package com.workapi.model

import moe.banana.jsonapi2.Resource
import java.lang.Exception
import java.util.*

/**
 * The base class for all top-level resources coming from the Work-API
 *
 * This class handles (de)serialization of the ID for retrieval of the integration ID and remote ID
 *
 * The superclass Resource handles processing of JSONAPI data. Subclasses describe and annotate fields
 * as described in the moshi-jsonapi package docs.
 *
 * @see <a href="https://github.com/kamikat/moshi-jsonapi">https://github.com/kamikat/moshi-jsonapi</a>
 * @version 0.1
 */
open class WorkApiResource : Resource() {
    @Transient private var remoteId: String = ""
    @Transient private var integrationId = ""

    init { updateId() }

    fun getIntegrationId(): String {
        if (integrationId.isEmpty()) {
            updateId()
        }

        return integrationId
    }

    fun setIntegrationId(value: String) {
        updateId(integrationId = value)
    }

    fun getRemoteId(): String {
        if (remoteId.isEmpty()) {
            updateId()
        }

        return remoteId
    }

    fun setRemoteId(value: String) {
        updateId(remoteId = value)
    }

    private fun updateId(integrationId: String = "", remoteId: String = "") {
        val decodedId = getIdSegments()

        if (integrationId.isNotEmpty()) {
            decodedId[0] = integrationId
        }

        if (remoteId.isNotEmpty()) {
            decodedId[1] = remoteId
        }

        this.integrationId = decodedId[0]
        this.remoteId = decodedId[1]

        id = Base64.getUrlEncoder()
            .encodeToString(
                decodedId.joinToString(":").toByteArray()
            )
    }

    private fun getIdSegments(): MutableList<String> {
        return try {
            val decodedIdBytes = Base64.getUrlDecoder().decode(id)
            String(decodedIdBytes).split(":").toMutableList()
        } catch(e: Exception) {
            listOf<String>("", "").toMutableList()
        }
    }
}
