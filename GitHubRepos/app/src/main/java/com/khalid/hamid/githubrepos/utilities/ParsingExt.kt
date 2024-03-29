/*
 * Copyright 2022 Mohammed Khalid Hamid.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.khalid.hamid.githubrepos.utilities

import com.google.gson.Gson
import java.io.InputStream
import java.nio.charset.Charset

fun InputStream.readTextAndClose(charset: Charset = Charsets.UTF_8): String {
    return this.bufferedReader(charset).use { it.readText() }
}

// for testing
inline fun <reified T> parseJsonFromResource(classLoader: ClassLoader?, jsonPath: String): T {
    var mockJson = classLoader?.getResourceAsStream(jsonPath)
        ?.readTextAndClose()
    return Gson().fromJson(mockJson, T::class.java)
}

inline fun <reified T> parseJsonFromAsset(classLoader: ClassLoader?, jsonPath: String): T {
    var mockJson = classLoader?.getResourceAsStream(jsonPath)
        ?.readTextAndClose()
    return Gson().fromJson(mockJson, T::class.java)
}
