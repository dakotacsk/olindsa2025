package org.example

/**
 * Represents a square matrix of a given size.
 *
 * @property size The number of rows and columns in the matrix.
 * @property data The 2D array storing the matrix elements.
 */
class Matrix(val size: Int) {
    val data: Array<DoubleArray> = Array(size) { DoubleArray(size) }

    /**
     * Gets the value at the specified row and column.
     * @param row The row index.
     * @param col The column index.
     * @return The value at the given indices.
     */
    operator fun get(row: Int, col: Int): Double {
        return data[row][col]
    }

    /**
     * Sets the value at the specified row and column.
     * @param row The row index.
     * @param col The column index.
     * @param value The value to set.
     */
    operator fun set(row: Int, col: Int, value: Double) {
        data[row][col] = value
    }

    /**
     * Splits the matrix into four sub-matrices of size n/2 x n/2.
     * @return A list of four matrices: top-left, top-right, bottom-left, bottom-right.
     */
    fun split(): List<Matrix> {
        val newSize = size / 2
        val a11 = Matrix(newSize)
        val a12 = Matrix(newSize)
        val a21 = Matrix(newSize)
        val a22 = Matrix(newSize)

        for (i in 0 until newSize) {
            for (j in 0 until newSize) {
                a11[i, j] = this[i, j]
                a12[i, j] = this[i, j + newSize]
                a21[i, j] = this[i + newSize, j]
                a22[i, j] = this[i + newSize, j + newSize]
            }
        }
        return listOf(a11, a12, a21, a22)
    }

    /**
     * Combines four sub-matrices into a single larger matrix.
     * @param c11 Top-left sub-matrix.
     * @param c12 Top-right sub-matrix.
     * @param c21 Bottom-left sub-matrix.
     * @param c22 Bottom-right sub-matrix.
     * @return A new matrix of combined size.
     */
    companion object {
        fun combine(c11: Matrix, c12: Matrix, c21: Matrix, c22: Matrix): Matrix {
            val newSize = c11.size * 2
            val result = Matrix(newSize)
            val halfSize = c11.size

            for (i in 0 until halfSize) {
                for (j in 0 until halfSize) {
                    result[i, j] = c11[i, j]
                    result[i, j + halfSize] = c12[i, j]
                    result[i + halfSize, j] = c21[i, j]
                    result[i + halfSize, j + halfSize] = c22[i, j]
                }
            }
            return result
        }
    }

    /**
     * Adds two matrices.
     * @param other The matrix to add.
     * @return A new matrix representing the sum, or null if dimensions are incompatible.
     */
    operator fun plus(other: Matrix): Matrix? {
        if (size != other.size) return null
        val result = Matrix(size)
        for (i in 0 until size) {
            for (j in 0 until size) {
                result[i, j] = this[i, j] + other[i, j]
            }
        }
        return result
    }

    /**
     * Subtracts two matrices.
     * @param other The matrix to subtract.
     * @return A new matrix representing the difference, or null if dimensions are incompatible.
     */
    operator fun minus(other: Matrix): Matrix? {
        if (size != other.size) return null
        val result = Matrix(size)
        for (i in 0 until size) {
            for (j in 0 until size) {
                result[i, j] = this[i, j] - other[i, j]
            }
        }
        return result
    }

    /**
     * Multiplies [this] matrix by [other] using the standard algorithm.
     * @return [this] * [other] if dimensions are compatible, null otherwise.
     */
    fun multiply(other: Matrix): Matrix? {
        if (size != other.size) return null
        val result = Matrix(size)
        for (i in 0 until size) {
            for (j in 0 until size) {
                for (k in 0 until size) {
                    result[i, j] += this[i, k] * other[k, j]
                }
            }
        }
        return result
    }

    /**
     * Multiplies [this] matrix by [other] using a pure Strassen's algorithm.
     * Recurses down to 1x1 matrices.
     * @return [this] * [other] if dimensions are compatible, null otherwise.
     */
    fun strassenMultiply(other: Matrix): Matrix? {
        if (size != other.size) return null

        // Base case for recursion: 1x1 matrix
        if (size == 1) {
            val result = Matrix(1)
            result[0, 0] = this[0, 0] * other[0, 0]
            return result
        }

        val (a11, a12, a21, a22) = this.split()
        val (b11, b12, b21, b22) = other.split()

        val p1 = (a11 + a22)!!.strassenMultiply((b11 + b22)!!)
        val p2 = (a21 + a22)!!.strassenMultiply(b11)
        val p3 = a11.strassenMultiply((b12 - b22)!!)
        val p4 = a22.strassenMultiply((b21 - b11)!!)
        val p5 = (a11 + a12)!!.strassenMultiply(b22)
        val p6 = (a21 - a11)!!.strassenMultiply((b11 + b12)!!)
        val p7 = (a12 - a22)!!.strassenMultiply((b21 + b22)!!)

        // All additions and subtractions are between matrices of the same size,
        // so the results will not be null. We can use !! safely.
        val c11 = ((p1!! + p4!!)!! - p5!!)!! + p7!!
        val c12 = p3!! + p5!!
        val c21 = p2!! + p4!!
        val c22 = ((p1!! - p2!!)!! + p3!!)!! + p6!!

        return combine(c11!!, c12!!, c21!!, c22!!)
    }

    /**
     * Multiplies [this] matrix by [other] using a hybrid Strassen's algorithm.
     * Switches to standard multiplication for smaller matrices.
     * @return [this] * [other] if dimensions are compatible, null otherwise.
     */
    fun hybridStrassenMultiply(other: Matrix): Matrix? {
        if (size != other.size) return null

        // Base case for recursion
        if (size <= 32) { // Threshold for switching
            return this.multiply(other)
        }

        val (a11, a12, a21, a22) = this.split()
        val (b11, b12, b21, b22) = other.split()

        val p1 = (a11 + a22)!!.hybridStrassenMultiply((b11 + b22)!!)
        val p2 = (a21 + a22)!!.hybridStrassenMultiply(b11)
        val p3 = a11.hybridStrassenMultiply((b12 - b22)!!)
        val p4 = a22.hybridStrassenMultiply((b21 - b11)!!)
        val p5 = (a11 + a12)!!.hybridStrassenMultiply(b22)
        val p6 = (a21 - a11)!!.hybridStrassenMultiply((b11 + b12)!!)
        val p7 = (a12 - a22)!!.hybridStrassenMultiply((b21 + b22)!!)

        // All additions and subtractions are between matrices of the same size,
        // so the results will not be null. We can use !! safely.
        val c11 = ((p1!! + p4!!)!! - p5!!)!! + p7!!
        val c12 = p3!! + p5!!
        val c21 = p2!! + p4!!
        val c22 = ((p1!! - p2!!)!! + p3!!)!! + p6!!

        return combine(c11!!, c12!!, c21!!, c22!!)
    }

    override fun toString(): String {
        return data.joinToString("") { row -> row.joinToString(" ") }
    }
}
