package com.example.hexdec

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

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

        answerInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val answer = answerInput.text.toString()
                val userWasCorrect =
                    compareAnswerAndDisplayedNumber(currentGameMode, answer, displayedNumber)
                if (userWasCorrect) {
                    currentGameMode = getCurrentGameMode(userGameMode)
                    displayedNumber = setNewRandomNumber(maxNumber, currentGameMode)
                    answerInput.text = ""
                }
                showKeyboard(currentGameMode)
            }
            true
        }

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

}
