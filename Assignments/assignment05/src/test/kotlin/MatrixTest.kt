package org.example

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MatrixTest {

    @Test
    fun `test matrix creation and get-set`() {
        val matrix = Matrix(2)
        matrix[0, 0] = 1.0
        matrix[0, 1] = 2.0
        matrix[1, 0] = 3.0
        matrix[1, 1] = 4.0

        assertEquals(1.0, matrix[0, 0])
        assertEquals(2.0, matrix[0, 1])
        assertEquals(3.0, matrix[1, 0])
        assertEquals(4.0, matrix[1, 1])
    }

    @Test
    fun `test matrix addition`() {
        val a = Matrix(2)
        a[0, 0] = 1.0; a[0, 1] = 2.0
        a[1, 0] = 3.0; a[1, 1] = 4.0

        val b = Matrix(2)
        b[0, 0] = 5.0; b[0, 1] = 6.0
        b[1, 0] = 7.0; b[1, 1] = 8.0

        val expected = Matrix(2)
        expected[0, 0] = 6.0; expected[0, 1] = 8.0
        expected[1, 0] = 10.0; expected[1, 1] = 12.0

        val result = a + b
        assertArrayEquals(expected.data[0], result!!.data[0])
        assertArrayEquals(expected.data[1], result.data[1])
    }

    @Test
    fun `test standard matrix multiplication`() {
        val a = Matrix(2)
        a[0, 0] = 1.0; a[0, 1] = 2.0
        a[1, 0] = 3.0; a[1, 1] = 4.0

        val b = Matrix(2)
        b[0, 0] = 5.0; b[0, 1] = 6.0
        b[1, 0] = 7.0; b[1, 1] = 8.0

        val expected = Matrix(2)
        expected[0, 0] = 19.0; expected[0, 1] = 22.0
        expected[1, 0] = 43.0; expected[1, 1] = 50.0

        val result = a.multiply(b)
        assertArrayEquals(expected.data[0], result!!.data[0], 0.001)
        assertArrayEquals(expected.data[1], result.data[1], 0.001)
    }

    @Test
    fun `test strassen matrix multiplication`() {
        val a = Matrix(2)
        a[0, 0] = 1.0; a[0, 1] = 2.0
        a[1, 0] = 3.0; a[1, 1] = 4.0

        val b = Matrix(2)
        b[0, 0] = 5.0; b[0, 1] = 6.0
        b[1, 0] = 7.0; b[1, 1] = 8.0

        val expected = Matrix(2)
        expected[0, 0] = 19.0; expected[0, 1] = 22.0
        expected[1, 0] = 43.0; expected[1, 1] = 50.0

        val result = a.strassenMultiply(b)
        assertArrayEquals(expected.data[0], result!!.data[0], 0.001)
        assertArrayEquals(expected.data[1], result.data[1], 0.001)
    }
}
