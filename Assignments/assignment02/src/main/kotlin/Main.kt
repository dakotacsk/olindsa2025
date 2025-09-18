package org.example

class DoublyLinkedListImpl<T> : DoublyLinkedList<T> {
    override var head: DoublyLinkedList.Node<T>? = null
    override var bottom: DoublyLinkedList.Node<T>? = null
}

class MyStack<T> : Stack<T> {
    private val list = DoublyLinkedListImpl<T>()

    override fun push(data: T) {
        list.pushFront(data)
    }

    override fun pop(): T? {
        return list.popFront()
    }

    override fun peek(): T? {
        return list.peekFront()
    }

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }
}

class MyQueue<T> : Queue<T> {
    private val list = DoublyLinkedListImpl<T>()

    override fun enqueue(data: T) {
        list.pushBack(data)
    }

    override fun dequeue(): T? {
        return list.popFront()
    }

    override fun peek(): T? {
        return list.peekFront()
    }

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }
}

fun isValid(s: String): Boolean {
    val stack = MyStack<Char>()
    val map = mapOf('(' to ')', '{' to '}', '[' to ']')

    for (char in s) {
        if (map.containsKey(char)) {
            stack.push(char)
        } else if (stack.isEmpty() || map[stack.pop()] != char) {
            return false
        }
    }

    return stack.isEmpty()
}

fun main() {
    val testCases = listOf(
        "()" to true,
        "()[]{}" to true,
        "(]" to false,
        "([)]" to false,
        "{[]}" to true,
        "" to true,
        "[" to false,
        "]" to false
    )

    testCases.forEach { (input, expected) ->
        val result = isValid(input)
        println("Input: \"$input\" | Expected: $expected | Got: $result -> ${if (result == expected) "PASS" else "FAIL"}")
    }
}