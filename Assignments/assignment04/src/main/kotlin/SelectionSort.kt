package main

/**
 * Sorts a list of integers in place using the selection sort algorithm.
 *
 * @param list The list of integers to sort.
 * @return The sorted list.
 */
fun selectionSort(list: MutableList<Int>): MutableList<Int> {
    for (i in 0 until list.size - 1) {
        var minIdx = i
        for (j in i + 1 until list.size) {
            if (list[j] < list[minIdx]) {
                minIdx = j
            }
        }
        if (minIdx != i) {
            val tmp = list[i]
            list[i] = list[minIdx]
            list[minIdx] = tmp
        }
    }
    return list
}