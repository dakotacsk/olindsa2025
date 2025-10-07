package sort

/**
 * Sorts a list of integers using the merge sort algorithm.
 * This is not an in-place sort.
 *
 * @param a The list of integers to sort.
 * @return A new list containing the sorted integers.
 */
fun mergeSort(a: MutableList<Int>): MutableList<Int> {
    if (a.size <= 1) return a
    val mid = a.size / 2
    val l = mergeSort(a.subList(0, mid).toMutableList())
    val r = mergeSort(a.subList(mid, a.size).toMutableList())
    return merge(l, r)
}

/**
 * Merges two sorted lists into a new sorted list.
 *
 * @param l The first sorted list.
 * @param r The second sorted list.
 * @return A new sorted list containing all elements from l and r.
 */
private fun merge(l: MutableList<Int>, r: MutableList<Int>): MutableList<Int> {
    val out = ArrayList<Int>(l.size + r.size)
    var i = 0; var j = 0
    while (i < l.size && j < r.size) {
        if (l[i] <= r[j]) out.add(l[i++]) else out.add(r[j++])
    }
    while (i < l.size) out.add(l[i++])
    while (j < r.size) out.add(r[j++])
    return out
}