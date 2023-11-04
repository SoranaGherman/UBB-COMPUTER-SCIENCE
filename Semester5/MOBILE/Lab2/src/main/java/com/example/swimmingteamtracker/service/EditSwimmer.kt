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
            val updatedSwimmer = Swimmer(
                fullNameTextInputEdit.editText?.text.toString(),
                genderTextInputEdit.editText?.text.toString(),
                nationalityTextInputEdit.editText?.text.toString(),
                weightTextInputEdit.editText?.text.toString().toDouble(),
                heightTextInputEdit.editText?.text.toString().toInt(),
                selectedSwimmer!!.id
            )

            val resultIntent = Intent(this, MainActivity::class.java)
            resultIntent.putExtra("updatedSwimmer", updatedSwimmer)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}












