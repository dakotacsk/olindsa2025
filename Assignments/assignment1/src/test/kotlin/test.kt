import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertSame

private fun arr2(vararg rows: IntArray) = arrayOf(*rows)

private fun assertMatrixEquals(expected: Array<IntArray>, actual: Array<IntArray>) {
    assertEquals(expected.size, actual.size, "row count mismatch")
    for (r in expected.indices) {
        assertContentEquals(expected[r], actual[r], "row $r mismatch")
    }
}

class FloodFillTest {

    @Test
    fun `fills disconnected region starting in corner`() {
        val image = arr2(
            intArrayOf(1, 1, 1),
            intArrayOf(1, 0, 1),
            intArrayOf(0, 1, 0)
        )
        val result = floodFill(image, sr = 0, sc = 0, newColor = 5)

        val expected = arr2(
            intArrayOf(5, 5, 5),
            intArrayOf(5, 0, 5),
            intArrayOf(0, 1, 0)
        )
        assertMatrixEquals(expected, result)
    }

    @Test
    fun `no-op when newColor equals old`() {
        val image = arr2(
            intArrayOf(4, 1, 1),
            intArrayOf(1, 0, 1),
            intArrayOf(0, 1, 0)
        )
        val copyBefore = arr2(
            intArrayOf(4, 1, 1),
            intArrayOf(1, 0, 1),
            intArrayOf(0, 1, 0)
        )
        val result = floodFill(image, sr = 0, sc = 0, newColor = 4)

        assertMatrixEquals(copyBefore, result) // unchanged
    }

    @Test
    fun `fills entire grid when all same color`() {
        val image = arr2(
            intArrayOf(1, 1, 1),
            intArrayOf(1, 1, 1),
            intArrayOf(1, 1, 1)
        )
        val result = floodFill(image, sr = 0, sc = 0, newColor = 9)

        val expected = arr2(
            intArrayOf(9, 9, 9),
            intArrayOf(9, 9, 9),
            intArrayOf(9, 9, 9)
        )
        assertMatrixEquals(expected, result)
    }

    @Test
    fun `fills plus shape from center only`() {
        val image = arr2(
            intArrayOf(0, 1, 0),
            intArrayOf(1, 1, 1),
            intArrayOf(0, 1, 0)
        )
        val result = floodFill(image, sr = 1, sc = 1, newColor = 5)

        val expected = arr2(
            intArrayOf(0, 5, 0),
            intArrayOf(5, 5, 5),
            intArrayOf(0, 5, 0)
        )
        assertMatrixEquals(expected, result)
    }

    @Test
    fun `touching border respects bounds`() {
        val image = arr2(
            intArrayOf(1, 4, 1),
            intArrayOf(3, 2, 1),
            intArrayOf(1, 1, 1)
        )
        val result = floodFill(image, sr = 0, sc = 0, newColor = 5)

        val expected = arr2(
            intArrayOf(5, 4, 1),
            intArrayOf(3, 2, 1),
            intArrayOf(1, 1, 1)
        )
        assertMatrixEquals(expected, result)
    }

    @Test
    fun `returns same instance (in-place mutation contract)`() {
        val image = arr2(
            intArrayOf(1, 1, 1),
            intArrayOf(1, 0, 1),
            intArrayOf(0, 1, 0)
        )
        val returned = floodFill(image, sr = 0, sc = 0, newColor = 7)
        assertSame(image, returned, "function should mutate and return the same matrix instance")
    }

    @Test
    fun `single cell grid`() {
        val image = arr2(intArrayOf(3))
        val result = floodFill(image, 0, 0, 8)
        assertMatrixEquals(arr2(intArrayOf(8)), result)
    }
}
