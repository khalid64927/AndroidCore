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

package com.khalid.hamid.githubrepos.utilities.extentions

fun Int?.orZero(): Int = this ?: 0

/**
 * Convert server int value where result is mapped to true
 * when value == 1
 */
fun Int.toBoolean(): Boolean = this == 1

fun IntArray.exceptFirst(): IntArray {
    return this.slice(1 until size)
        .toIntArray()
}
