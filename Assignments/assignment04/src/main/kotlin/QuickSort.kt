package main

/**
 * Sorts a list of integers in place using the quick sort algorithm.
 *
 * @param list The list of integers to sort.
 * @return The sorted list.
 */
fun quickSort(list: MutableList<Int>): MutableList<Int> {
    quickSortHelper(list, 0, list.lastIndex)
    return list
}

/**
 * Helper function for quick sort. Sorts a sublist of the given list.
 *
 * @param list The list containing the sublist to sort.
 * @param low The starting index of the sublist.
 * @param high The ending index of the sublist.
 */
private fun quickSortHelper(list: MutableList<Int>, low: Int, high: Int) {
    if (low < high) {
        val pivotIndex = partition(list, low, high)
        quickSortHelper(list, low, pivotIndex - 1)
        quickSortHelper(list, pivotIndex + 1, high)
    }
}

/**
 * Partitions a sublist of the given list around a pivot.
 *
 * @param list The list containing the sublist to partition.
 * @param low The starting index of the sublist.
 * @param high The ending index of the sublist.
 * @return The index of the pivot element after partitioning.
 */
private fun partition(list: MutableList<Int>, low: Int, high: Int): Int {
    val pivot = list[high]
    var i = low - 1
    for (j in low until high) {
        if (list[j] <= pivot) {
            i++
            val tmp = list[i]
            list[i] = list[j]
            list[j] = tmp
        }
    }
    val tmp = list[i + 1]
    list[i + 1] = list[high]
    list[high] = tmp
    return i + 1
}