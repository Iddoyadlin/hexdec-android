package com.boss.hexdec

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get

const val GAME_MODE = "GAME_MODE"
const val MAX_NUM = "MAX_NUM"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val maxNumInput: EditText = findViewById(R.id.max_num_input)
        maxNumInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val play: View = findViewById(R.id.play)
                play.isEnabled = !maxNumInput.text.isBlank()
            }
        })
    }

    private fun getGameMode(): Int {
        val group: RadioGroup = findViewById(R.id.myRadioGroup)
        for (i in 0 until 3) {
            val button = group[i] as RadioButton
            if (button.isChecked) {
                return i
            }
        }
        return -1

    }

    fun onPlayClick(view: View?) {
        val maxNumInput: EditText = findViewById(R.id.max_num_input)
        val maxNumber = Integer.parseInt(maxNumInput.text.toString())
        val gameMode = getGameMode()
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(GAME_MODE, gameMode)
        intent.putExtra(MAX_NUM, maxNumber)
        startActivity(intent)
    }


}

