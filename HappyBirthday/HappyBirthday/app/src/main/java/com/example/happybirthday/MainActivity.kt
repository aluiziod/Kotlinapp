package com.example.happybirthday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.TextView




public class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonPickName : Button = findViewById(R.id.buttonName)
        val buttonNext:Button = findViewById(R.id.buttonNext)

        buttonPickName.setOnClickListener {
            Toast.makeText(this,"Name inserted successfully",Toast.LENGTH_SHORT).show()
            val name:EditText = findViewById(R.id.name)
            val getName: TextView = findViewById(R.id.textviewNameDisplay)

            // variable str is receiving the content of the edittext
           var str:String = name.text.toString()
            //putting variable into textview
            getName.setText(str)
            buttonNext.setVisibility(View.VISIBLE)

        }

        val secondActivityButton : Button = findViewById(R.id.buttonNext)
        secondActivityButton.setOnClickListener {
            val Intent = Intent(this,SecondActivity::class.java)
            startActivity(Intent)
        }
    }
}