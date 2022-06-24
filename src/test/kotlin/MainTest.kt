package org.cufy.weakness.test

import org.cufy.weakness.Weakness
import org.cufy.weakness.test.mock.Entity
import org.cufy.weakness.test.mock.name
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MainTest {
    @Test
    fun basicSetAndGetThenClean() {
        // done like this to prevent inline
        val block = {
            val entity = Entity()
            entity.name = "MyEntityName"
            assertEquals("MyEntityName", entity.name, "Get not working properly")
        }
        block()
        System.gc()
        // The size is 1 but the entry is not there
        assertTrue(Weakness.map.toMutableMap().isEmpty(), "Cleaning didn't work.")
    }
}
