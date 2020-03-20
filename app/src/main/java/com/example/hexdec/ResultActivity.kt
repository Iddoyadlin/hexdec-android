package com.example.hexdec

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils




const val myPrefsKey = "HEXDEC"
const val bestScoreKey = "BEST_SCORE"

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val score = intent.extras!!.getString(SCORE)
        val scoreView: TextView = findViewById(R.id.score)
        scoreView.text = score
        val bestScoreView: TextView = findViewById(R.id.best_score_value)
        val newBestScore = updateBestScore(Integer.parseInt(score!!))
        bestScoreView.text = newBestScore.toString()

    }

    private fun updateBestScore(score: Int): Int {
        val currBestScore = getCurrentBestScore()
        if (score > currBestScore) {
            editBestScore(score)
            return score
        }
        return currBestScore


    }

    private fun getCurrentBestScore(): Int {
        val prefs = getSharedPreferences(myPrefsKey, Context.MODE_PRIVATE)
        return prefs.getInt(bestScoreKey, 0)
    }

    private fun editBestScore(score: Int) {
        val prefs = getSharedPreferences(myPrefsKey, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt(bestScoreKey, score)
        editor.apply()
    }


    fun clickMainMenu(view: View?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }


    fun clickPlayAgain(view: View?) {
        val intent = NavUtils.getParentActivityIntent(this)
        intent!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        NavUtils.navigateUpTo(this, intent)
    }
}
