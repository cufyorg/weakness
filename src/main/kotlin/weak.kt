/*
 *	Copyright 2022 cufy.org
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package org.cufy.weakness

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.cast
import kotlin.reflect.jvm.jvmErasure

/**
 * Create a property delegate to bind a field to
 * access data from the receiver weakness instance.
 *
 * The given [defaultValue] function will be invoked
 * lazily to get the value when accessed before
 * being set.
 *
 * When the value is accessed before being set for
 * the first time and [defaultValue] is null,
 * a [NullPointerException] will be thrown with a
 * proper message.
 *
 * @receiver the weakness instance.
 * @param defaultName pass to override the property name.
 * @param defaultType pass to override the property type.
 * @param defaultValue a function to lazily initialize
 *                the field with.
 * @since 1.0.0
 */
fun <T> weak(
    defaultName: String? = null,
    defaultType: KType? = null,
    defaultValue: (() -> T)? = null
) = weak(
    weakness = Weakness,
    defaultName = defaultName,
    defaultType = defaultType,
    defaultValue = defaultValue
)

/**
 * Create a property delegate to bind a field to
 * access data from the given [weakness] instance.
 *
 * The given [defaultValue] function will be invoked
 * lazily to get the value when accessed before
 * being set.
 *
 * When the value is accessed before being set for
 * the first time and [defaultValue] is null,
 * a [NullPointerException] will be thrown with a
 * proper message.
 *
 * @param defaultName pass to override the property name.
 * @param defaultType pass to override the property type.
 * @param weakness the weakness instance.
 * @param defaultValue a function to lazily initialize
 *                the field with.
 * @since 1.0.0
 */
fun <T> weak(
    weakness: Weakness,
    defaultName: String? = null,
    defaultType: KType? = null,
    defaultValue: (() -> T)? = null
) = object : ReadWriteProperty<Any, T> {
    override fun getValue(instance: Any, property: KProperty<*>): T {
        val type = defaultType ?: property.returnType
        val name = defaultName ?: property.name
        val map = weakness[instance]

        if (name !in map) {
            defaultValue ?: throw NullPointerException(
                "Property ${defaultName ?: property.name} is not initialized"
            )

            val value = defaultValue()
            map[name] = value
            return value
        }

        @Suppress("UNCHECKED_CAST")
        val value = map[name] as T

        if (value != null || !type.isMarkedNullable) {
            type.jvmErasure.cast(value)
        }

        return value
    }

    override fun setValue(instance: Any, property: KProperty<*>, value: T) {
        val type = defaultType ?: property.returnType
        val name = defaultName ?: property.name
        val map = weakness[instance]

        if (value != null || !type.isMarkedNullable) {
            type.jvmErasure.cast(value)
        }

        map[name] = value
    }
}
