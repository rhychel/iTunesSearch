package com.rhymartmanchus.itunessearch.data.api

import com.google.gson.*
import com.rhymartmanchus.itunessearch.data.models.TrackRaw
import java.lang.reflect.Type

class TrackRawConverter : JsonDeserializer<List<TrackRaw>> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<TrackRaw> {
        val gson = GsonBuilder().create()
        val response = json.asJsonObject
        val results = response.getAsJsonArray("results")
        val tracks = mutableListOf<TrackRaw>()

        results.forEach {
            tracks.add(gson.fromJson(it, TrackRaw::class.java))
        }

        return tracks
    }

}