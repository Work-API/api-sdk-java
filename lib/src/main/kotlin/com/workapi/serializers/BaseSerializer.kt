package com.workapi.serializers

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import moe.banana.jsonapi2.Document
import moe.banana.jsonapi2.Resource
import moe.banana.jsonapi2.ResourceAdapterFactory

open class BaseSerializer(
    protected var typeClass: Any
) {
    fun serializeDocument(document: Document): String {
        return adapter().toJson(document)
    }

    protected open fun adapter(): JsonAdapter<Document> {
        return moshi().adapter(Document::class.java)
    }

    protected fun moshi(): Moshi {
        return Moshi.Builder()
            .add(adapterFactory().build())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    protected open fun adapterFactory(): ResourceAdapterFactory.Builder {
        return ResourceAdapterFactory.builder()
            .add(typeClass as Class<out Resource>)
    }
}
