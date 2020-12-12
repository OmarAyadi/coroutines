package com.moa.coroutines.global

/**
 * @author omar Dec 11, 2020
 *
 * @File contains template functions
 */


inline fun invalid(
    fieldName: String,
    value: Any
): String {
    return "invalid $fieldName : $value"
}

inline fun successful(
    type: String = "deleted",
    value: Any
): String {
    return "$value $type successfully"
}

inline fun exists(
    fieldName: String,
    value: Any
): String {
    return "$fieldName $value already exists"
}