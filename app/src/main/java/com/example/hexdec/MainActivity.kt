package com.example.hexdec

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import java.io.File

val GAME_MODE = "GAME_MODE"
val MAX_NUM = "MAX_NUM"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val max_num_input: EditText = findViewById(R.id.max_num_input)
        max_num_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val play: View = findViewById(R.id.play)
                if (max_num_input.text.isBlank()) {
                    play.isEnabled = false
                } else {
                    play.isEnabled = true

                }
            }
        })
    }

    private fun getGameMode(): Int {
        val rgroup: RadioGroup = findViewById(R.id.myRadioGroup)
        for (i in 0 until 3) {
            val button: RadioButton = rgroup[i] as RadioButton
            if (button.isChecked) {
                return i
            }
        }
        return -1

    }

    fun onPlayClick(view: View?) {
        val max_num_input: EditText = findViewById(R.id.max_num_input)
        val max_number = Integer.parseInt(max_num_input.text.toString())
        val game_mode = getGameMode()
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(GAME_MODE, game_mode)
        intent.putExtra(MAX_NUM, max_number)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


}

