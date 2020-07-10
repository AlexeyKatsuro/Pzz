package com.alexeykatsuro.pzz.utils

interface StorableEnum<out T> {
    val value: T
}


/**
 * Serialization and deserialization methods for [Enum] which implement [StorableEnum].
 */
inline fun <reified T, V> toEnum(value: V): T where T : StorableEnum<V>, T : Enum<T> {
    return enumValues<T>().find { it.value == value }
        ?: throw IllegalArgumentException("Unknown code: $value for ${T::class.java.simpleName}  enum")
}

inline fun <reified T, V> fromEnum(obj: T): V where T : StorableEnum<V>, T : Enum<T> {
    return obj.value
}

inline fun <reified T, V> toEnumOrNull(value: V): T? where T : StorableEnum<V>, T : Enum<T> {
    return enumValues<T>().find { it.value == value }
}

inline fun <reified T, V> toEnumOrDefault(
    value: V,
    default: T
): T where T : StorableEnum<V>, T : Enum<T> {
    return enumValues<T>().find { it.value == value } ?: default
}