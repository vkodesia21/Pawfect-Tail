package com.example.pawfecttail.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Animal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val yourName: String,

    @ColumnInfo(name = "city")
    val yourCity: String,

    @ColumnInfo(name = "description")
    val animalDesc: String,

    @ColumnInfo(name = "contact")
    val contact: String
        )
