package lpc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class WavIoTest {

    @Test
    fun testWavIo() {
        val samples = DoubleArray(1000) { kotlin.random.Random.nextDouble(-1.0, 1.0) * 32767 }
        val sampleRate = 8000
        val path = "test.wav"
        WavIo.writeWav(path, sampleRate, samples)
        val (readSamples, readSampleRate) = WavIo.readWav(path)
        assertEquals(sampleRate, readSampleRate)
        assertEquals(samples.size, readSamples.size)
        File(path).delete()
    }
}
