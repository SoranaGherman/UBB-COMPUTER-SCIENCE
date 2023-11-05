package com.example.swimmingteamtracker.adapter

import android.content.Intent
import com.example.swimmingteamtracker.data.Swimmer
import com.example.swimmingteamtracker.R
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import com.example.swimmingteamtracker.service.EditSwimmer
import com.google.android.material.imageview.ShapeableImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.swimmingteamtracker.service.DeleteSwimmer


class SwimmerAdapter(private val swimmers: MutableList<Swimmer>) :
    RecyclerView.Adapter<SwimmerAdapter.SwimmerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwimmerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.swimmer_cardview, parent, false)
        return SwimmerViewHolder(view)
    }

    override fun onBindViewHolder(holder: SwimmerViewHolder, position: Int) {
        val swimmer = swimmers[position]
        holder.bind(swimmer)
    }

    override fun getItemCount(): Int {
        return swimmers.size
    }

    inner class SwimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val swimmerNameTextView: TextView = itemView.findViewById(R.id.swimmerNameTextView)

        fun bind(swimmer: Swimmer) {
            swimmerNameTextView.text = swimmer.fullName
        }

        init {
            val editIcon = itemView.findViewById<ShapeableImageView>(R.id.editIconTextView)
            editIcon.setOnClickListener {
                val swimmer = swimmers[adapterPosition]

                val intent = Intent(itemView.context, EditSwimmer::class.java)
                intent.putExtra("selectedSwimmer", swimmer)
                (itemView.context as AppCompatActivity).startActivityForResult(intent, 124)
            }

            val deleteIcon = itemView.findViewById<ShapeableImageView>(R.id.deleteIconTextView)
            deleteIcon.setOnClickListener {
                val swimmer = swimmers[adapterPosition]

                val intent = Intent(itemView.context, DeleteSwimmer::class.java)
                intent.putExtra("deletedSwimmer", swimmer)
                (itemView.context as AppCompatActivity).startActivityForResult(intent, 125)
            }
        }
    }

    fun addSwimmer(swimmer: Swimmer) {
        swimmers.add(swimmer)
        notifyDataSetChanged()
    }
}
