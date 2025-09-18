
package org.example

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class StackTest {

    @Test
    fun `test push and peek`() {
        val stack = MyStack<Int>()
        stack.push(1)
        assertEquals(1, stack.peek())
        stack.push(2)
        assertEquals(2, stack.peek())
    }

    @Test
    fun `test pop`() {
        val stack = MyStack<String>()
        stack.push("a")
        stack.push("b")
        assertEquals("b", stack.pop())
        assertEquals("a", stack.pop())
    }

    @Test
    fun `test isEmpty`() {
        val stack = MyStack<Double>()
        assertTrue(stack.isEmpty())
        stack.push(3.14)
        assertFalse(stack.isEmpty())
    }

    @Test
    fun `test pop from empty stack`() {
        val stack = MyStack<Int>()
        assertNull(stack.pop())
    }

    @Test
    fun `test peek from empty stack`() {
        val stack = MyStack<Int>()
        assertNull(stack.peek())
    }

    @Test
    fun `test complex sequence`() {
        val stack = MyStack<Int>()
        stack.push(10)
        stack.push(20)
        assertEquals(20, stack.pop())
        stack.push(30)
        assertEquals(30, stack.peek())
        assertEquals(30, stack.pop())
        assertEquals(10, stack.pop())
        assertTrue(stack.isEmpty())
    }
}
