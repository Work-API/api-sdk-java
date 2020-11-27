package com.workapi.serializers

import com.workapi.model.ItemIdList
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ItemIdListSerializer {
    fun serializePayload(payload: ItemIdList): String {
        return adapter().toJson(payload)
    }

    fun deserializePayload(jsonPayload: String): ItemIdList? {
        val fixedPayload = jsonPayload
            .replace("emails:", """"emails":""")
            .replace("events:", """"events":""")
        return adapter().fromJson(fixedPayload)
    }

    private fun adapter(): JsonAdapter<ItemIdList> {
        return moshi().adapter(ItemIdList::class.java)
    }

    private fun moshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}
