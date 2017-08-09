package kotiln.com.baselibrary.tools.data

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.lang.reflect.Type
import java.util.*

/**
 * Created by fulixin on 2017/7/11.
 * 数据封装解析处理类
 */
interface IDataInface {
    /**
     * 实体类转json字符串
     */
    fun <T> entityToJsonStr(entity: T): String

    /**
     * 实体类转json
     */
    fun <T> entityToJson(entity: T): JsonObject

    /**
     * json转实体类
     */
    fun <T> jsonToEntity(jsonObject: JsonObject,type: Type): T

    /**
     * json字符串转实体类
     */
    fun <T> jsonStrToEntity(jsonObject: String,type: Type): T

    /**
     * json转map
     */
    fun <T> jsonToMap(jsonObject: JsonObject,type: Type): Map<String, T>

    /**
     * json字符串转map
     */
    fun <T> jsonStrToMap(jsonObject: String,type: Type): Map<String, T>

    /**
     * json转String
     */
    fun <T> jsonToString(jsonObject: JsonObject,type: Type): T

    /**
     * map转json
     */
    fun <T> mapToJson(map: Map<String, T>): JsonObject

    /**
     * map转json字符串
     */
    fun <T> mapToJsonStr(map: Map<String, T>): String

    /**
     * list转jsonarray字符串
     */
    fun <T> listToJsonArrayStr(list: ArrayList<T>): String

    /**
     * list转jsonarray
     */
    fun <T> listToJsonArray(list: ArrayList<T>): JsonArray

    /**
     * jsonarray转String
     */
    fun <T> jsonArrayToString(jsonArray: JsonArray,type: Type): T

    /**
     * jsonarray转list
     */
    fun <T> jsonArrayToList(jsonArray: JsonArray,type: Type): ArrayList<T>
}