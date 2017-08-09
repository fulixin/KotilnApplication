package kotiln.com.baselibrary.tools.data.impl

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotiln.com.baselibrary.tools.data.IDataInface
import java.lang.reflect.Type

/**
 * Created by fulixin on 2017/7/11.
 */
class DataInfaceImpl : IDataInface {
    override fun <T> entityToJsonStr(entity: T): String {
        var gson: Gson = Gson()
        return gson.toJson(entity)
    }

    override fun <T> entityToJson(entity: T): JsonObject {
        var jsonParser: JsonParser = JsonParser()
        return jsonParser.parse(entityToJsonStr(entity)).asJsonObject
    }

    override fun <T> jsonToEntity(jsonObject: JsonObject, type: Type): T {
        var gson: Gson = Gson()
        return gson.fromJson<T>(jsonObject, type)
    }

    override fun <T> jsonStrToEntity(jsonObject: String, type: Type): T {
        var gson: Gson = Gson()
        return gson.fromJson<T>(jsonObject, type)
    }

    override fun <T> jsonStrToMap(jsonObject: String, type: Type): Map<String, T> {
        var gson: Gson = Gson()
        return gson.fromJson<Map<String, T>>(jsonObject, type)
    }

    override fun <T> jsonToMap(jsonObject: JsonObject, type: Type): Map<String, T> {
        var gson: Gson = Gson()
        return gson.fromJson<Map<String, T>>(jsonObject, type)
    }

    override fun <T> jsonToString(jsonObject: JsonObject, type: Type): T {
        var gson: Gson = Gson()
        return gson.fromJson<T>(jsonObject, type)
    }

    override fun <T> mapToJsonStr(map: Map<String, T>): String {
        var gson: Gson = Gson()
        return gson.toJson(map)
    }

    override fun <T> mapToJson(map: Map<String, T>): JsonObject {
        var jsonParser: JsonParser = JsonParser()
        return jsonParser.parse(mapToJsonStr(map)).asJsonObject
    }

    override fun <T> listToJsonArrayStr(list: ArrayList<T>): String {
        var gson: Gson = Gson()
        return gson.toJson(list)
    }

    override fun <T> listToJsonArray(list: ArrayList<T>): JsonArray {
        var jsonParser: JsonParser = JsonParser()
        return jsonParser.parse(listToJsonArrayStr(list)).asJsonArray
    }

    override fun <T> jsonArrayToString(jsonArray: JsonArray, type: Type): T {
        var gson: Gson = Gson()
        return gson.fromJson<T>(jsonArray, type)
    }

    override fun <T> jsonArrayToList(jsonArray: JsonArray, type: Type): ArrayList<T> {
        var gson: Gson = Gson()
        return gson.fromJson<ArrayList<T>>(jsonArray, type)
    }
}