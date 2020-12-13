package com.moa.coroutines.global

/**
 * @author omar Dec 11, 2020
 *
 * @File contains template functions
 */


fun invalid(fieldName: String, value: Any) = "invalid $fieldName : $value"

fun successful(type: String = "deleted", value: Any) = "$value $type successfully"

fun exists(fieldName: String, value: Any) = "$fieldName $value already exists"

fun cannotBeNull(fieldName: String) = "$fieldName cannot be null"

fun cannotBeBlank(fieldName: String) = "$fieldName cannot be null or blank"