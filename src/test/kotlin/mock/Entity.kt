package org.cufy.weakness.test.mock

import org.cufy.weakness.Weakness
import org.cufy.weakness.weak

val MyClosedWeakness = Weakness()

class Entity

var Entity.name: String by weak()

var Entity.nullableName: String? by weak()

var Entity.closedName: String by weak(MyClosedWeakness, "name")
