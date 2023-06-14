package com.example.bestiario_dq_app.data.mappers

import androidx.room.TypeConverter
import com.example.bestiario_dq_app.data.remote.responses.Atributo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromAtributosList(atributos: List<Atributo>): String {
        return gson.toJson(atributos)
    }

    @TypeConverter
    fun toAtributosList(json: String): List<Atributo> {
        val type = object : TypeToken<List<Atributo>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }
}