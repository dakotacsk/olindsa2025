// FloodFill.kt

fun floodFill(image: Array<IntArray>, sr: Int, sc: Int, newColor: Int): Array<IntArray> {
    val rows = image.size
    val cols = image[0].size
    val old = image[sr][sc]
    if (old == newColor) return image

    fun fill(r: Int, c: Int) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) return
        if (image[r][c] != old) return
        image[r][c] = newColor
        fill(r - 1, c)
        fill(r + 1, c)
        fill(r, c - 1)
        fill(r, c + 1)
    }

    fill(sr, sc)
    return image
}

fun printMatrix(m: Array<IntArray>) {
    m.forEach { row -> println(row.joinToString(" ")) }
    println()
}

fun arr2(vararg rows: IntArray) = arrayOf(*rows)

fun main() {
    var image = arr2(intArrayOf(1,1,1), intArrayOf(1,0,1), intArrayOf(0,1,0))
    printMatrix(floodFill(image, 0, 0, 5))

    image = arr2(intArrayOf(4,1,1), intArrayOf(1,0,1), intArrayOf(0,1,0))
    printMatrix(floodFill(image, 0, 0, 5))

    image = arr2(intArrayOf(1,1,1), intArrayOf(1,1,1), intArrayOf(1,1,1))
    printMatrix(floodFill(image, 0, 0, 5))

    image = arr2(intArrayOf(0,1,0), intArrayOf(1,1,1), intArrayOf(0,1,0))
    printMatrix(floodFill(image, 1, 1, 5))

    image = arr2(intArrayOf(1,4,1), intArrayOf(3,2,1), intArrayOf(1,1,1))
    printMatrix(floodFill(image, 0, 0, 5))
}