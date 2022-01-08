package com.example.pawfecttail

import androidx.lifecycle.*
import com.example.pawfecttail.data.Animal
import com.example.pawfecttail.data.AnimalDao
import kotlinx.coroutines.launch

class AnimalViewModel(private val animalDao:AnimalDao): ViewModel() {

    val allAnimals: LiveData<List<Animal>> = animalDao.getAllAnimals().asLiveData()

    fun updateAnimal (
        animalId: Int,
        name: String,
        city: String,
        contact: String,
        desc: String
    ) {
        val updatedAnimal = getUpdatedAnimalEntry(animalId,name,city,contact,desc)
        updateAnimal(updatedAnimal)
    }

    fun addNewAnimal(name: String, city: String, desc: String,contact: String) {
        val newAnimal = getNewAnimalEntry(name, city, desc,contact)
        addAnimal(newAnimal)
    }

    fun deleteAnimal(animal: Animal){
        viewModelScope.launch {
            animalDao.delete(animal)
        }
    }

    fun addAnimal(animal: Animal){
        viewModelScope.launch {
            animalDao.insert(animal)
        }
    }
    fun retrieveItem(id: Int): LiveData<Animal> {
        return animalDao.getAnimal(id).asLiveData()
    }
    fun isEntryValid(yourName: String, yourCity: String, desc: String,contact: String): Boolean {
        if (yourName.isBlank() || yourCity.isBlank() || desc.isBlank()|| contact.isBlank()) {
            return false
        }
        return true
    }

    private fun updateAnimal(animal: Animal){
        viewModelScope.launch {
            animalDao.update(animal)
        }
    }
    private fun getNewAnimalEntry(name: String, city: String, desc: String,contact: String): Animal {
        return Animal(
            yourName = name,
            yourCity = city ,
            animalDesc = desc,
            contact = contact
        )
    }

    private fun getUpdatedAnimalEntry(
        animalId: Int,
        name: String,
        city: String,
        time: String,
        desc: String): Animal {
        return Animal(
            id = animalId,
            yourName = name,
            yourCity = city,
            contact = time,
            animalDesc = desc
        )
    }

}
class AnimalViewModelFactory(private val animalDao: AnimalDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnimalViewModel(animalDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
