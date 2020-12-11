package com.moa.coroutines.models

import java.io.Serializable

/**
 * @author omar Dec 11, 2020
 */

// `Response` contains an arbitrary serialized server response data along with a
// error that describes an occurred error.
//
//
// JSON
// ====
// The JSON representation of an `Response` value uses the regular
// representation of the deserialized, embedded message, Example:
//
//
// * Response<String> for a string server response message =>
// {
//      "data"  : "Operation Successful",
//      "error" : null
// }
//
// * Response with error : the user provided an invalid id
// {
//      "data"  : null,
//      "error" : "invalid id : 123"
// }
//
open class Response<T>(
    // #Represent the generic object that will be identified at compile time
    // -> this attribute will hold success data information
    var data: T? = null,
    // #Resprense a detailed information about the error that occurred at run time
    var error: String? = null
) : Serializable

// #Extension Value that reduce Response creation in the code
// Exp of conversion
//  * T is a String : "Operation Successful"
//  * calling res on the object will return a Response object
//  * "Operation Successful".res => Response(data = "Operation Successful")
// JSON representation
// {
//      "data"  : "Operation Successful",
//      "error" : null
// }
val <T> T.res: Response<T>
    get() = Response(data = this)




