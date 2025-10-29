package lpc

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

class DspTest {

    @Test
    fun testLstsq() {
        val X = arrayOf(
            doubleArrayOf(1.0, 1.0),
            doubleArrayOf(1.0, 2.0),
            doubleArrayOf(1.0, 3.0)
        )
        val y = doubleArrayOf(1.0, 2.0, 3.0)
        val expected = doubleArrayOf(0.0, 1.0)
        val actual = Dsp.LinAlg.lstsq(X, y)
        assertArrayEquals(expected, actual, 1e-9)
    }
}
