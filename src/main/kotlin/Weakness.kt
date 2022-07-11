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

import com.google.common.collect.MapMaker

/**
 * A global instance to store additional data of
 * objects.
 *
 * @author LSafer
 * @since 1.0.0
 */
open class Weakness {
    /**
     * The default weakness instance.
     *
     * @since 1.0.0
     */
    companion object : Weakness()

    /**
     * The map holding the weak field values.
     *
     * @since 1.0.0
     */
    internal val map: MutableMap<Any, MutableMap<String, Any?>> =
        MapMaker().weakKeys().makeMap()

    /**
     * Return the map backing weak properties of
     * the given [instance] in this weakness
     * instance.
     *
     * @since 1.0.0
     */
    operator fun get(instance: Any): MutableMap<String, Any?> {
        return map.getOrPut(instance) { mutableMapOf() }
    }
}
