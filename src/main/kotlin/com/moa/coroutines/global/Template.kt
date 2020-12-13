package com.moa.coroutines.global

/**
 * @author omar Dec 11, 2020
 *
 * @File contains template functions
 */


fun invalid(
    fieldName: String,
    value: Any
): String {
    return "invalid $fieldName : $value"
}

fun successful(
    type: String = "deleted",
    value: Any
): String {
    return "$value $type successfully"
}

fun exists(
    fieldName: String,
    value: Any
): String {
    return "$fieldName $value already exists"
}