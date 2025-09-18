package org.example

interface DoublyLinkedList<T> {
    data class Node<T>(
        val data: T,
        var next: Node<T>? = null,
        var prev: Node<T>? = null
    )

    var head: Node<T>?
    var bottom: Node<T>?
    /**
     * Adds the element [data] to the front of the linked list.
     */
    fun pushFront(data: T){
        val newNode = Node(data, next = head)
        if (head != null) {
            head!!.prev = newNode
        }
        head = newNode
        if (bottom == null) { // first element
            bottom = newNode
        }
    }

    /**
     * Adds the element [data] to the back of the linked list.
     */
    fun pushBack(data: T){
        val newNode = Node(data, prev = bottom)
        if (bottom != null) {
            bottom!!.next = newNode
        }
        bottom = newNode
        if (head == null) { // first element
            head = newNode
        }

    }

    /**
     * Removes an element from the front of the list. If the list is empty, it is unchanged.
     * @return the value at the front of the list or nil if none exists
     */
    fun popFront(): T?{
        val value = head?.data
        head = head?.next

        if (head == null){
            bottom = null
        } else {
            head!!.prev = null
        }

        return value
    }

    /**
     * Removes an element from the back of the list. If the list is empty, it is unchanged.
     * @return the value at the back of the list or nil if none exists
     */
    fun popBack(): T?{
        val value = bottom?.data
        bottom = bottom?.prev

        if (bottom == null){
            head = null
        } else  {
            bottom!!.next = null
        }

        return value
    }

    /**
     * @return the value at the front of the list or nil if none exists
     */
    fun peekFront(): T?{
        return head?.data
    }

    /**
     * @return the value at the back of the list or nil if none exists
     */
    fun peekBack(): T?{
        return bottom?.data
    }

    /**
     * @return true if the list is empty and false otherwise
     */
    fun isEmpty(): Boolean{
        return head == null
    }
}
