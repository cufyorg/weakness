package org.cufy.weakness.test.mock

import org.cufy.weakness.WeakProperty
import org.cufy.weakness.Weakness

val MyClosedWeakness = Weakness()

class Entity

var Entity.name: String by WeakProperty()

var Entity.nullableName: String? by WeakProperty()

var Entity.closedName: String by MyClosedWeakness.WeakProperty("name")
