package com.example.hexdec

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.random.Random

const val SCORE = "SCORE"

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val userGameMode = intent.extras!!.getInt(GAME_MODE)
        val maxNumber = intent.extras!!.getInt(MAX_NUM)
        val answerInput: TextView = findViewById(R.id.answer)

        var currentGameMode = getCurrentGameMode(userGameMode)
        var displayedNumber = setNewRandomNumber(maxNumber, currentGameMode)
        showKeyboard(currentGameMode)
        startTimer(10)
        answerInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val answer = answerInput.text.toString()
                val userWasCorrect =
                    compareAnswerAndDisplayedNumber(currentGameMode, answer, displayedNumber)
                if (userWasCorrect) {
                    currentGameMode = getCurrentGameMode(userGameMode)
                    displayedNumber = setNewRandomNumber(maxNumber, currentGameMode)
                    increaseScore()
                }
                answerInput.text = ""
                showKeyboard(currentGameMode)
            }
            true
        }

    }
//
//    override fun onResume() {
//        super.onResume()
//        val answerInput: TextView = findViewById(R.id.answer)
//        answerInput.text = "333"
//    }

    private fun startTimer(time: Int) {
        val timer = Timer()
        val task = object : TimerTask() {
            var timePassed = 0
            val timerView: TextView = findViewById(R.id.timer)
            override fun run() {
                runOnUiThread(object : TimerTask() {
                    override fun run() {
                        val timeLeft = time - timePassed
                        if (timeLeft < 0) {
                            val intent = Intent(this@GameActivity, ResultActivity::class.java)
                            val scoreView: TextView = findViewById(R.id.score)
                            val score = scoreView.text
                            intent.putExtra(SCORE, score)
                            timer.cancel()
                            hideKeyboard()
                            startActivity(intent)
                        } else {
                            timerView.text = timeLeft.toString()
                            timePassed++
                        }
                    }
                });
            }
        }
        timer.schedule(task, 0, 1000)
    }

    private fun increaseScore() {
        val scoreView: TextView = findViewById(R.id.score)
        val newScore = Integer.parseInt(scoreView.text.toString()) + 1
        scoreView.text = newScore.toString()

    }

    private fun getCurrentGameMode(userGameMode: Int): Int {
        return if (userGameMode == 2) {
            Random.nextInt(0, 2)
        } else {
            userGameMode
        }
    }

    private fun setNewRandomNumber(maxNumber: Int, gameMode: Int): Int {
        val n = Random.nextInt(0, maxNumber + 1)
        val nView: TextView = findViewById(R.id.rand_num)
        val text = if (gameMode == 0) "0x" + Integer.toHexString(n) else n.toString()
        nView.text = text
        return n
    }

    private fun compareAnswerAndDisplayedNumber(gameMode: Int, answer: String, displayedNumber: Int)
            : Boolean {
        val base = if (gameMode == 1) 16 else 10
        return !answer.isBlank() && answer.toInt(base) == displayedNumber
    }


    private fun showKeyboard(gameMode: Int) {
        val answerInput: TextView = findViewById(R.id.answer)
        val mImm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        answerInput.inputType =
            if (gameMode == 0) InputType.TYPE_CLASS_NUMBER else InputType.TYPE_CLASS_TEXT
        mImm.showSoftInput(answerInput, InputMethodManager.SHOW_FORCED)
        answerInput.clearFocus()
        answerInput.requestFocus()
        mImm.restartInput(answerInput)
        answerInput.performClick()
    }

    private fun hideKeyboard() {
        val answerInput: TextView = findViewById(R.id.answer)
        answerInput.clearFocus()
        answerInput.requestFocus()
        val mImm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mImm.hideSoftInputFromWindow(answerInput.windowToken, 0);
    }


}
