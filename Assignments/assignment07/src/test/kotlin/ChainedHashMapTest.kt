import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ChainedHashMapTest {

    @Test
    fun testInsertAndGet() {
        val map = ChainedHashMap<String, Int>()

        map["apple"] = 1
        map["banana"] = 2
        map["cherry"] = 3

        assertEquals(3, map.size())
        assertTrue("banana" in map)
        assertEquals(2, map["banana"])
        assertNull(map["durian"])
    }

    @Test
    fun testUpdateExistingKey() {
        val map = ChainedHashMap<String, Int>()

        map["key"] = 10
        assertEquals(10, map["key"])

        map["key"] = 20
        assertEquals(20, map["key"])

        assertEquals(1, map.size())
    }

    @Test
    fun testRemove() {
        val map = ChainedHashMap<String, Int>()
        map["a"] = 100
        map["b"] = 200
        map["c"] = 300

        assertTrue(map.remove("b"))
        assertFalse(map.remove("b")) // already removed
        assertEquals(2, map.size())
        assertFalse("b" in map)
    }

    @Test
    fun testKeyValuePairs() {
        val map = ChainedHashMap<String, Int>()
        map["x"] = 9
        map["y"] = 8

        val pairs = map.keyValuePairs().toSet()
        assertTrue(pairs.contains("x" to 9))
        assertTrue(pairs.contains("y" to 8))
        assertEquals(2, pairs.size)
    }

    @Test
    fun testRehashing() {
        val map = ChainedHashMap<Int, Int>(initialCapacity = 3)

        // Insert a bunch to force rehash
        for (i in 0 until 100) {
            map[i] = i * 10
        }

        assertEquals(100, map.size())

        // Spot check a few
        assertEquals(0, map[0])
        assertEquals(50 * 10, map[50])
        assertEquals(99 * 10, map[99])
    }
}