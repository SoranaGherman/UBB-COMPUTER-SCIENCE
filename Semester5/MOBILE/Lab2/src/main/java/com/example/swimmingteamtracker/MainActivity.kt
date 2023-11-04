package com.example.swimmingteamtracker

import com.example.swimmingteamtracker.data.Swimmer
import com.example.swimmingteamtracker.adapter.SwimmerAdapter

import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import android.content.Intent
import com.example.swimmingteamtracker.service.AddSwimmer
import com.google.android.material.imageview.ShapeableImageView


class MainActivity : AppCompatActivity() {
    private val swimmersList = mutableListOf<Swimmer>()
    private val adapterSwimmersList = SwimmerAdapter(swimmersList)

    companion object {
        private const val ADD_SWIMMER_REQUEST_CODE = 123
        private const val EDIT_SWIMMER_REQUEST_CODE = 124
        private const val DELETE_SWIMMER_REQUEST_CODE = 125
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swimmersList.add(Swimmer("Ruxandra Brehar", "Female", "Country 1", 70.0, 180))
        swimmersList.add(Swimmer("Dragan Daria", "Female", "Country 2", 65.0, 170))
        swimmersList.add(Swimmer("David Popovici", "Male", "Country 1", 70.0, 180))
        swimmersList.add(Swimmer("Ropan Stefan", "Female", "Country 2", 65.0, 170))

        val recyclerView = findViewById<RecyclerView>(R.id.swimmerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapterSwimmersList


        val openAddScreenIcon = findViewById<ShapeableImageView>(R.id.addPersonIcon)

        openAddScreenIcon.setOnClickListener {
            val intent = Intent(this, AddSwimmer::class.java)
            startActivityForResult(intent, ADD_SWIMMER_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_SWIMMER_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                val newSwimmer = data.getParcelableExtra<Swimmer>("newSwimmer")
                if (newSwimmer != null) {
                    adapterSwimmersList.addSwimmer(newSwimmer)
                }
            }
        }

        if (requestCode == EDIT_SWIMMER_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                val updatedSwimmer = data.getParcelableExtra<Swimmer>("updatedSwimmer")
                if (updatedSwimmer != null) {
                    val index = swimmersList.indexOfFirst { it.id == updatedSwimmer.id}
                    if (index != -1) {
                        swimmersList[index] = updatedSwimmer
                        adapterSwimmersList.notifyItemChanged(index)
                    }
                }
            }
        }

        if (requestCode == DELETE_SWIMMER_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                val deletedSwimmer = data.getParcelableExtra<Swimmer>("deletedSwimmer")
                if (deletedSwimmer != null) {
                    swimmersList.remove(deletedSwimmer)
                    adapterSwimmersList.notifyItemRemoved(deletedSwimmer.id)
                }
            }
        }
    }
}
