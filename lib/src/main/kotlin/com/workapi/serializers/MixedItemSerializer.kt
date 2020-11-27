package com.workapi.serializers

import com.workapi.model.Email
import com.workapi.model.Event
import com.squareup.moshi.Moshi
import moe.banana.jsonapi2.ArrayDocument
import moe.banana.jsonapi2.Resource
import moe.banana.jsonapi2.ResourceAdapterFactory

class MixedItemSerializer: BaseSerializer(typeClass = Resource::class.java) {
    fun serializeItems(items: List<Resource>): String {
        val document: ArrayDocument<Resource> = ArrayDocument()
        document.addAll(items)
        return serializeDocument(document)
    }

    override fun adapterFactory(): ResourceAdapterFactory.Builder {
        return ResourceAdapterFactory.builder()
            .add(Email::class.java)
            .add(Event::class.java)
    }

    fun deserializeItems(body: String): List<Resource> {
        return adapter().fromJson(body)!!.asArrayDocument()
    }
}
