package com.example.pawfecttail

import android.app.Application
import com.example.pawfecttail.data.AnimalDatabase

class AnimalApplication: Application() {

    val database: AnimalDatabase by lazy { AnimalDatabase.getDatabase(this) }
}