package com.example.apprantice_02

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.os.CountDownTimer
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var score = 0

    private lateinit var gameScoreTextView: TextView
    private lateinit var timeLeftTextView: TextView
    private lateinit var tapMeButton: Button
    private lateinit var emojiView: TextView

    private var gameStarted = false
    private lateinit var countDownTimer: CountDownTimer
    private var initialCountDown: Long = 10000
    private var countDownInterval: Long = 1000
    private var timeLeft = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_text_view)
        tapMeButton = findViewById(R.id.tap_me_button)
        emojiView = findViewById(R.id.emoji_view)
        tapMeButton.setOnClickListener { incrementScore() }

        resetGame()
    }

    private fun incrementScore() {

        if (!gameStarted) {
            startGame()
        }

        score++

        val newScore = getString(R.string.your_score, score)
        gameScoreTextView.text = newScore

        gameScoreTextView.animate()
            .scaleX(1.3f)
            .scaleY(1.3f)
            .setDuration(150)
            .withEndAction {
                gameScoreTextView.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(150)
                    .start()
            }
            .start()
    }

    private fun resetGame() {
        score = 0

        val initialScore = getString(R.string.your_score, score)
        gameScoreTextView.text = initialScore

        val initialTimeLeft = getString(R.string.time_left, 60)
        timeLeftTextView.text = initialTimeLeft

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000

                val timeLeftString = getString(R.string.time_left, timeLeft)
                timeLeftTextView.text = timeLeftString
            }

            override fun onFinish() {
                endGame()
            }
        }

        gameStarted = false
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun showEmoji() {
        emojiView.alpha = 0f
        emojiView.visibility = TextView.VISIBLE
        emojiView.animate()
            .alpha(1f)
            .setDuration(500)
            .withEndAction {
                emojiView.animate()
                    .alpha(0f)
                    .setDuration(1000)
                    .withEndAction {
                        emojiView.visibility = TextView.GONE
                    }
                    .start()
            }
            .start()
    }


    private fun endGame() {
        Toast.makeText(this, getString(R.string.game_over_message, score), Toast.LENGTH_LONG).show()

        showEmoji()
    }
}