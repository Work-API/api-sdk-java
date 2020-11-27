package com.workapi.serializers

import com.workapi.model.User
import moe.banana.jsonapi2.ObjectDocument

class UserSerializer: BaseSerializer(typeClass = User::class.java) {
    fun serializeUser(user: User): String {
        val document: ObjectDocument<User> = ObjectDocument()
        document.set(user)
        return adapter().toJson(document)
    }

    fun deserializeUser(body: String): ObjectDocument<User> {
        return adapter().fromJson(body)!!.asObjectDocument()
    }
}
