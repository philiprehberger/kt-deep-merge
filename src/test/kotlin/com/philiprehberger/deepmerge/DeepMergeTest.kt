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

    // diff tests
    @Test fun `diff identical maps`() {
        val a = mapOf("a" to 1, "b" to 2)
        assertEquals(emptyMap(), diff(a, a))
    }
    @Test fun `diff changed value`() {
        val a = mapOf("a" to 1, "b" to 2)
        val b = mapOf("a" to 1, "b" to 99)
        assertEquals(mapOf("b" to 99), diff(a, b))
    }
    @Test fun `diff added key`() {
        val a = mapOf("a" to 1)
        val b = mapOf("a" to 1, "b" to 2)
        assertEquals(mapOf("b" to 2), diff(a, b))
    }
    @Test fun `diff removed key not included`() {
        val a = mapOf("a" to 1, "b" to 2)
        val b = mapOf("a" to 1)
        assertEquals(emptyMap(), diff(a, b))
    }
    @Test fun `diff nested maps`() {
        val a = mapOf("x" to mapOf("a" to 1, "b" to 2))
        val b = mapOf("x" to mapOf("a" to 1, "b" to 3, "c" to 4))
        val expected = mapOf("x" to mapOf("b" to 3, "c" to 4))
        assertEquals(expected, diff(a, b))
    }
    @Test fun `diff nested no change`() {
        val a = mapOf("x" to mapOf("a" to 1))
        val b = mapOf("x" to mapOf("a" to 1))
        assertEquals(emptyMap(), diff(a, b))
    }
    @Test fun `diff null values`() {
        val a = mapOf<String, Any?>("a" to 1)
        val b = mapOf<String, Any?>("a" to null)
        assertEquals(mapOf<String, Any?>("a" to null), diff(a, b))
    }
    @Test fun `diff empty maps`() {
        assertEquals(emptyMap(), diff(emptyMap(), emptyMap()))
    }
}
