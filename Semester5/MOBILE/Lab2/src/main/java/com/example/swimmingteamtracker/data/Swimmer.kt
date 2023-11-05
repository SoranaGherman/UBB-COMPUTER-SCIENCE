package com.example.swimmingteamtracker.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Swimmer(
    var fullName: String, var gender: String, var nationality: String, var weight: Double,
    var height: Int, var id: Int) : Parcelable {

        companion object {
            var currentId = 0;
        }

        constructor(
            fullName: String, gender: String, nationality: String, weight: Double,
            height: Int
        ) : this(fullName, gender, nationality, weight, height, currentId++) {
            }
    }