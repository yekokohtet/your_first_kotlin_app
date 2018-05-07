package com.padcmyanmar.yourfirstkotlinapp

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
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
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            view.startAnimation(bounceAnimation)
            incrementScore()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_about) {
            showInfo()
        }
        return true
    }

    private fun showInfo() {
        val dialogTitle = getString(R.string.about_title, BuildConfig.VERSION_NAME)
        val dialogMessage = getString(R.string.about_message)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
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

        val blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink)
        tvYourScore.startAnimation(blinkAnimation)

    }
}
