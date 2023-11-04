package com.example.swimmingteamtracker.service

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.swimmingteamtracker.R
import com.example.swimmingteamtracker.data.Swimmer
import com.google.android.material.imageview.ShapeableImageView


class DeleteSwimmer : AppCompatActivity() {
    private var selectedSwimmer: Swimmer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_swimmer)

        val goBackArrow = findViewById<ShapeableImageView>(R.id.goBackArrow)

        goBackArrow.setOnClickListener {
            finish()
        }

        selectedSwimmer = intent.getParcelableExtra<Swimmer>("deletedSwimmer")
    }

    fun onDeleteButtonClick(view: View) {
        if (selectedSwimmer != null) {
            val resultIntent = Intent()
            resultIntent.putExtra("deletedSwimmer", selectedSwimmer)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    fun onCancelButtonClick(view: View) {
        finish()
    }
}
