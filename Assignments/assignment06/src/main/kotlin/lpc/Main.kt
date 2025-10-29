package lpc

import kotlin.math.floor

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: please provide a command (encode, decode, or compare) and the necessary arguments in your IDE's run configuration.")
        return
    }

    when (args[0]) {
        "encode" -> {
            if (args.size < 3) {
                println("Usage: encode <input.wav> <output.lpc>")
                return
            }
            val inWav = args[1]
            val outLpc = args[2]

            val (samples, sampleRate) = WavIo.readWav(inWav)

            val maxAmp = samples.maxOfOrNull { kotlin.math.abs(it) } ?: 1.0
            val normalizedSamples = samples.map { 0.9 * it / maxAmp }.toDoubleArray()

            val targetSampleRate = 8000
            val resampled = WavIo.resample(normalizedSamples, sampleRate, targetSampleRate)

            val sym = false // periodic
            val w = hann(floor(0.03 * targetSampleRate).toInt(), sym)

            val p = 6 // number of poles
            val (A, G) = Lpc.lpc_encode(resampled, p, w)

            // Not implemented: writing LPC model to file
            println("Encoding not fully implemented.")
        }
        "decode" -> {
            if (args.size < 3) {
                println("Usage: decode <input.lpc> <output.wav>")
                return
            }
            // Not implemented: reading LPC model from file
            println("Decoding not fully implemented.")
        }
        "compare" -> {
            if (args.size < 2) {
                println("Usage: compare <input.wav>")
                return
            }
            val inWav = args[1]

            val (samples, sampleRate) = WavIo.readWav(inWav)

            val maxAmp = samples.maxOfOrNull { kotlin.math.abs(it) } ?: 1.0
            val normalizedSamples = samples.map { 0.9 * it / maxAmp }.toDoubleArray()

            val targetSampleRate = 8000
            val resampled = WavIo.resample(normalizedSamples, sampleRate, targetSampleRate)

            val sym = false // periodic
            val w = hann(floor(0.03 * targetSampleRate).toInt(), sym)

            val orders = listOf(8, 12, 16)

            for (p in orders) {
                println("Order $p:")
                val (A, G) = Lpc.lpc_encode(resampled, p, w)
                val xhat = Lpc.lpc_decode(A, G, w)

                val denormalizedXhat = xhat.map { it * maxAmp / 0.9 }.toDoubleArray()

                val (mse, snr) = Dsp.Metrics.compare(resampled, denormalizedXhat)
                println("  MSE: $mse")
                println("  SNR (dB): $snr")

                WavIo.writeWav("recon_order_${p}.wav", targetSampleRate, denormalizedXhat)
            }
        }
        else -> println("Unknown command: ${args[0]}")
    }
}

fun hann(n: Int, sym: Boolean): DoubleArray {
    val N = if (sym) n - 1 else n
    val w = DoubleArray(n)
    for (i in 0 until n) {
        w[i] = 0.5 * (1 - kotlin.math.cos(2.0 * kotlin.math.PI * i / N))
    }
    return w
}
