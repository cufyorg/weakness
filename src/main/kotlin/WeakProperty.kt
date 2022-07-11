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
package org.cufy.weakness

import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure
import kotlin.reflect.typeOf

/**
 * A property delegate to bind a field to access
 * data from a weakness instance.
 *
 * @author LSafer
 * @since 1.0.0
 */
@Deprecated("Old Weakness API. Will be removed on 2.0.0", level = DeprecationLevel.WARNING)
class WeakProperty<T>(
    /**
     * The name to associate the data with.
     */
    internal val name: String?,
    /**
     * The type of the data.
     */
    internal val type: KType,
    /**
     * The weakness instance.
     */
    internal val weakness: Weakness
) {
    operator fun getValue(instance: Any, property: KProperty<*>): T {
        val value = weakness[instance][name ?: property.name]

        if (value == null && !type.isMarkedNullable)
            throw NullPointerException("Property ${name ?: property.name} not initialized")

        if (value != null && !type.jvmErasure.isInstance(value))
            throw ClassCastException("Property ${name ?: property.name} has Illegal value type")

        @Suppress("UNCHECKED_CAST")
        return value as T
    }

    operator fun setValue(instance: Any, property: KProperty<*>, value: T?) {
        if (value == null && !type.isMarkedNullable)
            throw NullPointerException("Property ${name ?: property.name} is not nullable")

        if (value != null && !type.jvmErasure.isInstance(value))
            @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
            throw ClassCastException("Property ${name ?: property.name} of type ${type.jvmErasure} cannot accept value of type ${value!!::class.java}")

        weakness[instance][name ?: property.name] = value
    }
}

/**
 * Create a property delegate to bind a field to
 * access data from this weakness instance.
 *
 * @param name pass to override the property name.
 * @since 1.0.0
 */
@Suppress("Deprecation")
@Deprecated(
    "Old Weakness API. Will be removed on 2.0.0",
    ReplaceWith("weak(name)"),
    DeprecationLevel.WARNING
)
inline fun <reified T> Weakness.WeakProperty(
    name: String? = null,
): WeakProperty<T> {
    return WeakProperty(
        name = name,
        type = typeOf<T>(),
        weakness = this
    )
}

/**
 * Create a property delegate to bind a field to
 * access data from the default weakness instance.
 *
 * @param name pass to override the property name.
 * @since 1.0.0
 */
@Suppress("Deprecation")
@Deprecated(
    "Old Weakness API. Will be removed on 2.0.0",
    ReplaceWith("weak(name)"),
    DeprecationLevel.WARNING
)
inline fun <reified T> WeakProperty(
    name: String? = null,
): WeakProperty<T> {
    return WeakProperty(
        name = name,
        type = typeOf<T>(),
        weakness = Weakness
    )
}
