package com.philiprehberger.deepmerge

import kotlin.test.*

class DeepMergeTest {
    @Test fun `nested merge`() {
        val a = mapOf("x" to mapOf("a" to 1, "b" to 2))
        val b = mapOf("x" to mapOf("b" to 3, "c" to 4))
        val m = deepMerge(a, b)
        @Suppress("UNCHECKED_CAST") val x = m["x"] as Map<String, Any?>
        assertEquals(1, x["a"]); assertEquals(3, x["b"]); assertEquals(4, x["c"])
    }
    @Test fun `last wins`() {
        val m = deepMerge(mapOf("a" to 1), mapOf("a" to 2))
        assertEquals(2, m["a"])
    }
    @Test fun `first wins`() {
        val m = deepMerge(mapOf("a" to 1), mapOf("a" to 2)) { onConflict = MergeStrategy.FIRST_WINS }
        assertEquals(1, m["a"])
    }
    @Test fun `throw on conflict`() {
        assertFailsWith<MergeConflictException> {
            deepMerge(mapOf("a" to 1), mapOf("a" to 2)) { onConflict = MergeStrategy.THROW }
        }
    }
    @Test fun `list append`() {
        val m = deepMerge(mapOf("a" to listOf(1)), mapOf("a" to listOf(2))) { onListConflict = ListMerge.APPEND }
        assertEquals(listOf(1, 2), m["a"])
    }
    @Test fun `null skip`() {
        val m = deepMerge(mapOf("a" to 1), mapOf("a" to null)) { onNull = NullHandling.SKIP }
        assertEquals(1, m["a"])
    }
    @Test fun `variadic`() {
        val m = deepMerge(mapOf("a" to 1), mapOf("b" to 2), mapOf("c" to 3))
        assertEquals(1, m["a"]); assertEquals(2, m["b"]); assertEquals(3, m["c"])
    }
    @Test fun `empty`() = assertEquals(emptyMap(), deepMerge(emptyMap(), emptyMap()))
}
