package com.padcmyanmar.yourfirstkotlinapp

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    internal lateinit var btnTapMe: Button
    internal lateinit var tvYourScore: TextView
    internal lateinit var tvTimeLeft: TextView

    internal var score = 0

    internal var gameStarted = false
    internal lateinit var countDownTimer: CountDownTimer
    internal val initialCountDown: Long = 10000
    internal val countDownInterval: Long = 1000

    internal lateinit var disableBtnTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTapMe = findViewById(R.id.btnTapMe)
        tvYourScore = findViewById(R.id.tvYourScore)
        tvTimeLeft = findViewById(R.id.tvTimeLeft)
        resetGame()

        btnTapMe.setOnClickListener { view ->
            incrementScore()
        }
    }

    private fun resetGame() {
        score = 0
        tvYourScore.text = getString(R.string.your_score, score.toString())
        val initialTimerLeft = initialCountDown / 1000
        tvTimeLeft.text = getString(R.string.time_left, initialTimerLeft.toString())

        countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(p0: Long) {
                val timeLeft = p0 / 1000
                tvTimeLeft.text = getString(R.string.time_left, timeLeft.toString())
            }

            override fun onFinish() {
                endGame()
                disableBtnTimer.start()
            }
        }
        gameStarted = false
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.game_over_message, score.toString()), Toast.LENGTH_SHORT).show()
        disableTapMe()
        resetGame()
    }

    private fun disableTapMe() {
        disableBtnTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
                btnTapMe.isEnabled = false
            }

            override fun onFinish() {
                btnTapMe.isEnabled = true
            }
        }
    }

    private fun incrementScore() {
        if (!gameStarted) {
            startGame()
        }
        score += 1
        val newScore = getString(R.string.your_score, score.toString())
        tvYourScore.text = newScore

    }
}
