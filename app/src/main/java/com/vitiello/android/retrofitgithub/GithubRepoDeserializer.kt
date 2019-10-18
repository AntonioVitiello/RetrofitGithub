package com.vitiello.android.retrofitgithub

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
class GithubRepoDeserializer : JsonDeserializer<GithubRepo> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): GithubRepo {
        val githubRepo = GithubRepo()

        val repoJsonObject = json.asJsonObject
        githubRepo.name = repoJsonObject.get("name").asString
        githubRepo.url = repoJsonObject.get("url").asString

        val ownerJsonElement = repoJsonObject.get("owner")
        val ownerJsonObject = ownerJsonElement.asJsonObject
        githubRepo.owner = ownerJsonObject.get("login").asString

        return githubRepo
    }
}
