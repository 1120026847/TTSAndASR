package com.example.tts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText=findViewById<EditText>(R.id.editTextTextPersonName)
        val play=findViewById<Button>(R.id.button)
        play.setOnClickListener {
            TTSUtils.getInstance(this).play(editText.text.toString())
        }

    }
}