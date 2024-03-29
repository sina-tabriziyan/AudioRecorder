package ir.sina.audiorecord

import android.os.Handler
import android.os.Looper

class Timer(listener: OnTimerTickListener) {
    interface OnTimerTickListener {
        fun onTimerTick(duration: String)
    }

    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    private var duration = 0L
    private var delay = 100L

    init {
        runnable = Runnable {
            duration += delay
            handler.postDelayed(runnable, delay)
            listener.onTimerTick(format())
        }
    }

    fun start() {
        handler.postDelayed(runnable, delay)
    }

    fun pause() {
        handler.removeCallbacks(runnable)

    }

    fun stop() {
        handler.removeCallbacks(runnable)
        duration = 0L
    }

    private fun format(): String {
        val miliis = duration % 1000
        val seconds = (duration / 1000) % 60
        val minutes = (duration / (1000 * 60)) % 60
        val hours = duration / (1000 * 60 * 60)


        return if (hours > 0) "%0.2d:%02d:%02d.%02d".format(
            hours,
            minutes,
            seconds,
            miliis/10
        ) else "%02d:%02d.%02d".format(minutes, seconds, miliis/10)
    }

}