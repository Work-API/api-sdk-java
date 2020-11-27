package com.workapi.client.rest

import com.workapi.model.Metadata
import com.workapi.model.WorkApiResource
import com.workapi.client.rest.api.IResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import moe.banana.jsonapi2.Document

/**
 * @version 0.2
 */
class Response<T : WorkApiResource>(
    override val status: Int = -1,
    override var data: Document?
) : IResponse<T> {
    override val meta: Metadata? by lazy { parseMetadata() }

    private fun parseMetadata(): Metadata? {
        val meta = data?.meta
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<Metadata> = moshi.adapter<Metadata>(Metadata::class.java)
        return meta?.get<Metadata>(adapter) as Metadata?
    }

    override fun asMany(): List<T>? {
        return data?.asArrayDocument<T>()?.toMutableList()
    }

    override fun asSingle(): T? {
        return data?.asObjectDocument<T>()?.get()
    }
}
