package com.moa.coroutines.global

import com.moa.coroutines.models.ServerError
import com.moa.coroutines.models.badRequest

/**
 * @author omar Dec 11, 2020
 */

// #Summary method used validate a nullable field
// This validation method is usually used with dto objects to remove null variables
// Exp :
// Instance : Test(val name: String)
// dto : TestDto(val name: String?)
// -> we want to create a valid Test instance
// -> we call isValid on the name before creating the Test Instance
@Throws(ServerError::class)
fun Any?.isNotNull(fieldName: String) {
    badRequest(
        this == null,
        "$fieldName cannot be null"
    )
}

// #Summary method used validate a blank string
// This validation method is usually used with dto objects to remove blank strings
// Exp : create
// Instance : Test(val name: String)
// dto : TestDto(val name: String?)
// -> we want to create a valid Test instance with a not blank name
// -> we call isNotBlank on the name before creating the Test Instance
@Throws(ServerError::class)
fun String?.isNotBlank(fieldName: String) {
    // #Check if the field is null
    this.isNotNull(fieldName)
    // #Check if the field is blank
    this?.let {
        badRequest(
            isBlank(),
            "$fieldName cannot be blank"
        )
    }
}

// #Summary method used validate an email
// -> P.S: it's better to use an email confirmation rather than regex validation
@Throws(ServerError::class)
fun String?.isValidEmail() {
    // #Validate the email with a regex variable
    // todo add an email validation method
    val validEmailPattern = 
        "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])\n";

}

