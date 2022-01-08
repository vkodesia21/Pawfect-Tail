package com.example.pawfecttail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pawfecttail.data.Animal
import com.example.pawfecttail.databinding.AnimalRvBinding

class AnimalRVAdapter (private val onAnimalClicked: (Animal) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<Animal, AnimalRVAdapter.AnimalViewHolder>(DiffCallBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        return AnimalViewHolder(
            AnimalRvBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onAnimalClicked(current)
        }
        holder.bind(current)
    }

    class AnimalViewHolder(private var binding: AnimalRvBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(animal: Animal){
            binding.name.text = animal.yourName
            binding.city.text = animal.yourCity
            binding.contact.text = animal.contact
        }
        }
    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Animal>() {
            override fun areItemsTheSame(oldAnimal: Animal,newAnimal: Animal):Boolean{
                return oldAnimal==newAnimal
            }

            override fun areContentsTheSame(oldAnimal: Animal, newItem: Animal): Boolean {
                return oldAnimal.yourName == newItem.yourName
            }
        }
    }
    }
