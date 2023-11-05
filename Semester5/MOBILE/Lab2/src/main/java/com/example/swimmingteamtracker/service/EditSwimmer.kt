package com.example.swimmingteamtracker.service

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.swimmingteamtracker.MainActivity
import com.example.swimmingteamtracker.R
import com.example.swimmingteamtracker.data.Swimmer
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout
import android.view.View


class EditSwimmer : AppCompatActivity() {

    private var selectedSwimmer: Swimmer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_swimmer)

        val goBackArrow = findViewById<ShapeableImageView>(R.id.goBackArrow)

        goBackArrow.setOnClickListener {
          finish()
        }

        selectedSwimmer = intent.getParcelableExtra<Swimmer>("selectedSwimmer")

        val fullNameTextInputEdit = findViewById<TextInputLayout>(R.id.fullNameTextInputEdit)
        val genderTextInputEdit = findViewById<TextInputLayout>(R.id.genderTextInputEdit)
        val nationalityTextInputEdit = findViewById<TextInputLayout>(R.id.nationalityTextInputEdit)
        val weightTextInputEdit = findViewById<TextInputLayout>(R.id.weightTextInputEdit)
        val heightTextInputEdit = findViewById<TextInputLayout>(R.id.heightTextInputEdit)

        if (selectedSwimmer != null) {
            fullNameTextInputEdit.editText?.setText(selectedSwimmer!!.fullName)
            genderTextInputEdit.editText?.setText(selectedSwimmer!!.gender)
            nationalityTextInputEdit.editText?.setText(selectedSwimmer!!.nationality)
            weightTextInputEdit.editText?.setText(selectedSwimmer!!.weight.toString())
            heightTextInputEdit.editText?.setText(selectedSwimmer!!.height.toString())
        }
    }

    fun onUpdateButtonClick(view: View) {
        val fullNameTextInputEdit = findViewById<TextInputLayout>(R.id.fullNameTextInputEdit)
        val genderTextInputEdit = findViewById<TextInputLayout>(R.id.genderTextInputEdit)
        val nationalityTextInputEdit = findViewById<TextInputLayout>(R.id.nationalityTextInputEdit)
        val weightTextInputEdit = findViewById<TextInputLayout>(R.id.weightTextInputEdit)
        val heightTextInputEdit = findViewById<TextInputLayout>(R.id.heightTextInputEdit)

        if (selectedSwimmer != null) {

            val fullNameInput = fullNameTextInputEdit.editText?.text.toString()
            val genderInput = genderTextInputEdit.editText?.text.toString()
            val nationalityInput = nationalityTextInputEdit.editText?.text.toString()
            val weightInput = weightTextInputEdit.editText?.text.toString()
            val heightInput = heightTextInputEdit.editText?.text.toString()

            if (fullNameInput.isBlank() || genderInput.isBlank() || nationalityInput.isBlank() ||
                weightInput.isBlank() || heightInput.isBlank()) {
                if (fullNameInput.isBlank()) fullNameTextInputEdit.error = "Required"
                if (genderInput.isBlank()) genderTextInputEdit.error = "Required"
                if (nationalityInput.isBlank()) nationalityTextInputEdit.error = "Required"
                if (weightInput.isBlank()) weightTextInputEdit.error = "Required"
                if (heightInput.isBlank()) heightTextInputEdit.error = "Required"
            } else {
                try {
                    val weight = weightInput.toDouble()
                    val height = heightInput.toInt()

                    val updatedSwimmer = Swimmer(
                        fullNameInput,
                        genderInput,
                        nationalityInput,
                        weight,
                        height,
                        selectedSwimmer!!.id
                    )

                    val resultIntent = Intent(this, MainActivity::class.java)
                    resultIntent.putExtra("updatedSwimmer", updatedSwimmer)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                } catch (e: NumberFormatException) {
                    weightTextInputEdit.error = "Invalid number"
                    heightTextInputEdit.error = "Invalid number"
                }
            }
        }
    }
}



















