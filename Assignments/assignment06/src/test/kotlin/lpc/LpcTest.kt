package lpc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LpcTest {

    @Test
    fun testLpc() {
        val x = DoubleArray(1000) { kotlin.random.Random.nextDouble(-1.0, 1.0) }
        val w = Main.hann(240, false)
        val p = 6
        val (A, G) = Lpc.lpc_encode(x, p, w)
        val xhat = Lpc.lpc_decode(A, G, w)
        assertEquals(x.size, xhat.size)
    }
}
