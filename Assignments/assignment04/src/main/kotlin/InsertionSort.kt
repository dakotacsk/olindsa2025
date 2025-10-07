package main

/**
 * Sorts a list of integers in place using the insertion sort algorithm.
 *
 * @param list The list of integers to sort.
 * @return The sorted list.
 */
fun insertionSort(list: MutableList<Int>): MutableList<Int> {
    for (i in 1 until list.size) {
        val key = list[i]
        var j = i - 1
        while (j >= 0 && list[j] > key) {
            list[j + 1] = list[j]
            j--
        }
        list[j + 1] = key
    }
    return list
}