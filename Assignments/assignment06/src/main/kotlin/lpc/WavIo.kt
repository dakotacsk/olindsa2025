package lpc

import java.io.File
import javax.sound.sampled.AudioFileFormat
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import kotlin.math.max
import kotlin.math.min

data class WavData(val samples: DoubleArray, val sampleRate: Int)

object WavIo {

    fun readWav(wavPath: String): WavData {
        val inputFile = File(wavPath)
        val audioInputStream = AudioSystem.getAudioInputStream(inputFile)
        val format = audioInputStream.format

        if (format.channels != 1 || format.sampleSizeInBits != 16) {
            throw IllegalArgumentException("Only mono 16-bit WAV files are supported")
        }

        val audioBytes = audioInputStream.readAllBytes()
        val samples = DoubleArray(audioBytes.size / 2)
        for (i in samples.indices) {
            val lo = audioBytes[2 * i].toInt() and 0xFF
            val hi = audioBytes[2 * i + 1].toInt()
            val value = (hi shl 8) or lo
            samples[i] = value.toDouble()
        }

        audioInputStream.close()
        return WavData(samples, format.sampleRate.toInt())
    }

    fun writeWav(wavPath: String, sampleRate: Int, samples: DoubleArray) {
        val format = AudioFormat(sampleRate.toFloat(), 16, 1, true, false) // little-endian
        val audioBytes = ByteArray(samples.size * 2)
        for (i in samples.indices) {
            val value = samples[i].toInt()
            val clamped = min(32767, max(-32768, value))
            audioBytes[2 * i] = (clamped and 0xFF).toByte()
            audioBytes[2 * i + 1] = (clamped shr 8).toByte()
        }

        val audioInputStream = AudioInputStream(audioBytes.inputStream(), format, samples.size.toLong())
        val outputFile = File(wavPath)
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputFile)
        audioInputStream.close()
    }

    fun resample(samples: DoubleArray, origSampleRate: Int, targetSampleRate: Int): DoubleArray {
        if (origSampleRate == targetSampleRate) {
            return samples
        }
        val ratio = targetSampleRate.toDouble() / origSampleRate.toDouble()
        val newSize = (samples.size * ratio).toInt()
        val newSamples = DoubleArray(newSize)
        for (i in 0 until newSize) {
            val origIndex = i / ratio
            val index1 = kotlin.math.floor(origIndex).toInt()
            val index2 = kotlin.math.ceil(origIndex).toInt()
            if (index2 >= samples.size) {
                newSamples[i] = samples[index1]
            } else {
                val frac = origIndex - index1
                newSamples[i] = samples[index1] * (1 - frac) + samples[index2] * frac
            }
        }
        return newSamples
    }
}
