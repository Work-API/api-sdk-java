package com.workapi.serializers

import com.workapi.model.Email
import com.workapi.model.Integration
import moe.banana.jsonapi2.ArrayDocument
import moe.banana.jsonapi2.ObjectDocument

class IntegrationSerializer: BaseSerializer(typeClass = Integration::class.java) {
    fun serializeIntegration(integration: Integration): String {
        val document: ObjectDocument<Integration> = ObjectDocument()
        document.set(integration)
        return adapter().toJson(document)
    }

    fun deserializeIntegration(body: String): ObjectDocument<Integration> {
        return adapter().fromJson(body)!!.asObjectDocument()
    }

    fun serializeIntegrations(integrations: List<Integration>): String {
        val document: ArrayDocument<Integration> = ArrayDocument()
        document.addAll(integrations)
        return serializeDocument(document)
    }

    fun deserializeIntegrations(body: String): ArrayDocument<Integration> {
        return adapter().fromJson(body)!!.asArrayDocument()
    }
}
