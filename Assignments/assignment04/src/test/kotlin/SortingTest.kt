package test

import main.insertionSort
import main.quickSort
import main.selectionSort
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import sort.mergeSort

/**
 * Unit tests for the sorting algorithms.
 */
class SortingTest {

    private val algorithms = listOf(
        "InsertionSort" to ::insertionSort,
        "SelectionSort" to ::selectionSort,
        "QuickSort" to ::quickSort,
        "MergeSort" to ::mergeSort
    )

    /**
     * Tests that the sorting algorithms correctly handle an empty list.
     */
    @Test
    fun `test empty list`() {
        for ((name, algo) in algorithms) {
            val list = mutableListOf<Int>()
            val expected = emptyList<Int>()
            assertEquals(expected, algo(list), "Failed on $name with empty list")
        }
    }

    /**
     * Tests that the sorting algorithms correctly handle a list with a single element.
     */
    @Test
    fun `test single element list`() {
        for ((name, algo) in algorithms) {
            val list = mutableListOf(1)
            val expected = mutableListOf(1)
            assertEquals(expected, algo(list), "Failed on $name with single element list")
        }
    }

    /**
     * Tests that the sorting algorithms correctly handle a list that is already sorted.
     */
    @Test
    fun `test already sorted list`() {
        for ((name, algo) in algorithms) {
            val list = mutableListOf(1, 2, 3, 4, 5)
            val expected = mutableListOf(1, 2, 3, 4, 5)
            assertEquals(expected, algo(list), "Failed on $name with already sorted list")
        }
    }

    /**
     * Tests that the sorting algorithms correctly handle a list that is sorted in reverse order.
     */
    @Test
    fun `test reverse sorted list`() {
        for ((name, algo) in algorithms) {
            val list = mutableListOf(5, 4, 3, 2, 1)
            val expected = mutableListOf(1, 2, 3, 4, 5)
            assertEquals(expected, algo(list), "Failed on $name with reverse sorted list")
        }
    }

    /**
     * Tests that the sorting algorithms correctly handle a list with duplicate elements.
     */
    @Test
    fun `test list with duplicate elements`() {
        for ((name, algo) in algorithms) {
            val list = mutableListOf(5, 2, 3, 2, 1, 5)
            val expected = mutableListOf(1, 2, 2, 3, 5, 5)
            assertEquals(expected, algo(list), "Failed on $name with duplicate elements")
        }
    }

    /**
     * Tests that the sorting algorithms correctly handle a list with negative numbers.
     */
    @Test
    fun `test list with negative numbers`() {
        for ((name, algo) in algorithms) {
            val list = mutableListOf(-5, 2, -3, 0, 1)
            val expected = mutableListOf(-5, -3, 0, 1, 2)
            assertEquals(expected, algo(list), "Failed on $name with negative numbers")
        }
    }

    /**
     * Tests that the sorting algorithms correctly handle a typical unsorted list.
     */
    @Test
    fun `test typical unsorted list`() {
        for ((name, algo) in algorithms) {
            val list = mutableListOf(5, 1, 4, 2, 8)
            val expected = mutableListOf(1, 2, 4, 5, 8)
            assertEquals(expected, algo(list), "Failed on $name with typical unsorted list")
        }
    }
}
