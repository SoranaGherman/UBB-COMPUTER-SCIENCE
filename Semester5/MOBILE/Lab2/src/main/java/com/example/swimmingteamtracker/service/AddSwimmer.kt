package com.example.swimmingteamtracker.service

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.swimmingteamtracker.MainActivity
import com.example.swimmingteamtracker.R
import com.example.swimmingteamtracker.data.Swimmer
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout


class AddSwimmer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_swimmer)

        val goBackArrow = findViewById<ShapeableImageView>(R.id.goBackArrow)

        goBackArrow.setOnClickListener {
           finish()
        }

        val addSwimmerButton = findViewById<Button>(R.id.addSwimmerButton)
        addSwimmerButton.setOnClickListener {
            val fullNameInputLayout = findViewById<TextInputLayout>(R.id.fullNameTextInput)
            val genderInputLayout = findViewById<TextInputLayout>(R.id.genderTextInput)
            val nationalityInputLayout = findViewById<TextInputLayout>(R.id.nationalityTextInput)
            val weightInputLayout = findViewById<TextInputLayout>(R.id.weightTextInput)
            val heightInputLayout = findViewById<TextInputLayout>(R.id.heightTextInput)

            val fullNameInput = fullNameInputLayout.editText?.text.toString()
            val genderInput = genderInputLayout.editText?.text.toString()
            val nationalityInput = nationalityInputLayout.editText?.text.toString()
            val weightInput = weightInputLayout.editText?.text.toString()
            val heightInput = heightInputLayout.editText?.text.toString()

            if (fullNameInput.isBlank() || genderInput.isBlank() || nationalityInput.isBlank() ||
                weightInput.isBlank() || heightInput.isBlank()) {
                if (fullNameInput.isBlank()) fullNameInputLayout.error = "Required"
                if (genderInput.isBlank()) genderInputLayout.error = "Required"
                if (nationalityInput.isBlank()) nationalityInputLayout.error = "Required"
                if (weightInput.isBlank()) weightInputLayout.error = "Required"
                if (heightInput.isBlank()) heightInputLayout.error = "Required"
            } else {
                try {
                    val weight = weightInput.toDouble()
                    val height = heightInput.toInt()

                    val newSwimmer = Swimmer(fullNameInput, genderInput, nationalityInput, weight, height)

                    val resultIntent = Intent(this, MainActivity::class.java)
                    resultIntent.putExtra("newSwimmer", newSwimmer)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                } catch (e: NumberFormatException) {
                    weightInputLayout.error = "Invalid number"
                    heightInputLayout.error = "Invalid number"
                }
            }
        }
    }
}
