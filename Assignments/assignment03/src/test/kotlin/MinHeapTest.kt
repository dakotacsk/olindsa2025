import org.example.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MinHeapTest {

    @Test
    fun `min-heap basic ordering`() {
        val pq: MinPriorityQueue<String> = MinHeap()
        pq.addWithPriority("b", 2.0)
        pq.addWithPriority("a", 1.0)
        pq.addWithPriority("c", 3.0)

        assertEquals("a", pq.next())
        assertEquals("b", pq.next())
        assertEquals("c", pq.next())
        assertNull(pq.next())
        assertTrue(pq.isEmpty())
    }

    @Test
    fun `adjustPriority moves element appropriately`() {
        val pq: MinPriorityQueue<String> = MinHeap()
        pq.addWithPriority("x", 5.0)
        pq.addWithPriority("y", 2.0)
        pq.addWithPriority("z", 8.0)

        // Lower x to be the smallest
        pq.adjustPriority("x", 1.0)
        assertEquals("x", pq.next())
        assertEquals("y", pq.next())

        // Add a new element then raise its priority (becomes larger than z)
        pq.addWithPriority("w", 0.5)
        pq.adjustPriority("w", 10.0)
        assertEquals("z", pq.next())
        assertEquals("w", pq.next())
        assertTrue(pq.isEmpty())
    }
}