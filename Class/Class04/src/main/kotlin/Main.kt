package org.example
import org.example.Stack;

class MyStack<T>: Stack<T>{
    class StackNode<T>(val data: T,
                       var next: Stacknode<T>?)

    var top: StackNode<T>? = null

    override fun push(data: T){
        val newNode = StackNode(data,null)
        newNode.next = top
        top = newNode
    }

    override fun pop(): T?{
        val currVal = top?.data
        top = top?.next
        return currVal
    }

    override fun peek(): T?{
        return top?.data
    }

    override fun isEmpty(): Boolean{
        return top == null
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val name = "Kotlin"
    println("Hello, " + name + "!")

    for (i in 1..5) {
        println("i = $i")
    }
}