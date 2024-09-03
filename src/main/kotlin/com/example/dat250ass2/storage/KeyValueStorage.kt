package com.example.dat250ass2.storage

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

class KeyValueStorage<T>(namespace: String, private val valueType: Class<T>) {
    private val objectMapper = jacksonObjectMapper()
    private val file = File("$namespace.json")

    init {
        if (!file.exists()) {
            file.createNewFile()
            file.writeText("{}")
        }
    }

    fun put(
        key: String,
        value: T,
    ) {
        val map: MutableMap<String, T> = readMapFromFile()
        map[key] = value
        writeMapToFile(map)
    }

    fun get(key: String): T? {
        val map: Map<String, T> = readMapFromFile()
        return map[key]
    }

    fun delete(key: String): T? {
        val map: MutableMap<String, T> = readMapFromFile()
        val value = map.remove(key)
        writeMapToFile(map)
        return value
    }

    fun list(): List<T> {
        val map: Map<String, T> = readMapFromFile()
        return map.values.toList()
    }

    fun clear() {
        file.writeText("{}")
    }

    private fun readMapFromFile(): MutableMap<String, T> {
        if (file.length() == 0L) {
            return mutableMapOf()
        }

        val rootNode = objectMapper.readTree(file)
        val result = mutableMapOf<String, T>()

        rootNode.fields().forEachRemaining { entry ->
            val key = entry.key
            val valueNode: JsonNode = entry.value

            val value: T = objectMapper.treeToValue(valueNode, valueType)
            result[key] = value
        }

        return result
    }

    private fun writeMapToFile(map: Map<String, T>) {
        file.writeText(objectMapper.writeValueAsString(map))
    }
}
