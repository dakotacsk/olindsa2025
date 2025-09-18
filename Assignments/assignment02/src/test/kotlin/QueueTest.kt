
package org.example

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class QueueTest {

    @Test
    fun `test enqueue and peek`() {
        val queue = MyQueue<Int>()
        queue.enqueue(1)
        assertEquals(1, queue.peek())
        queue.enqueue(2)
        assertEquals(1, queue.peek()) // Front of the queue should still be 1
    }

    @Test
    fun `test dequeue`() {
        val queue = MyQueue<String>()
        queue.enqueue("a")
        queue.enqueue("b")
        assertEquals("a", queue.dequeue())
        assertEquals("b", queue.dequeue())
    }

    @Test
    fun `test isEmpty`() {
        val queue = MyQueue<Double>()
        assertTrue(queue.isEmpty())
        queue.enqueue(3.14)
        assertFalse(queue.isEmpty())
    }

    @Test
    fun `test dequeue from empty queue`() {
        val queue = MyQueue<Int>()
        assertNull(queue.dequeue())
    }

    @Test
    fun `test peek from empty queue`() {
        val queue = MyQueue<Int>()
        assertNull(queue.peek())
    }

    @Test
    fun `test complex sequence`() {
        val queue = MyQueue<Int>()
        queue.enqueue(10)
        queue.enqueue(20)
        assertEquals(10, queue.dequeue())
        queue.enqueue(30)
        assertEquals(20, queue.peek())
        assertEquals(20, queue.dequeue())
        assertEquals(30, queue.dequeue())
        assertTrue(queue.isEmpty())
    }
}
