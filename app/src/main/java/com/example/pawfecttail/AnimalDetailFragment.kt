package com.example.pawfecttail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pawfecttail.data.Animal
import com.example.pawfecttail.databinding.FragmentAnimalDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AnimalDetailFragment : Fragment() {
    private val navigationArgs: AnimalDetailFragmentArgs by navArgs()
    lateinit var animal: Animal

    private val viewModel: AnimalViewModel by activityViewModels {
        AnimalViewModelFactory(
            (activity?.application as AnimalApplication).database.getAnimalDao()
        )
    }

    private var _binding: FragmentAnimalDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAnimalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun bind(animal: Animal) {
        binding.apply {
            name.text = animal.yourName
            city.text = animal.yourCity
            desc.text = animal.animalDesc
            contact.text = animal.contact
            deleteItem.setOnClickListener { showConfirmationDialog() }
            editItem.setOnClickListener { editAnimal() }
        }
    }
    private fun editAnimal() {
        val action = AnimalDetailFragmentDirections.actionAnimalDetailFragmentToAddAnimalFragment(
            getString(R.string.edit_fragment_title),
            animal.id
        )
        this.findNavController().navigate(action)
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteAnimal()
            }
            .show()
    }

    private fun deleteAnimal() {
        viewModel.deleteAnimal(animal)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.animalId
        // Retrieve the item details using the itemId.
        // Attach an observer on the data (instead of polling for changes) and only update the
        // the UI when the data actually changes.
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            animal = selectedItem
            bind(animal)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}