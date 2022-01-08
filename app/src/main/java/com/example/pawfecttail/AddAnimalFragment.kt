package com.example.pawfecttail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.example.pawfecttail.data.Animal
import com.example.pawfecttail.databinding.FragmentAddAnimalBinding

class AddAnimalFragment : Fragment() {
    private val viewModel: AnimalViewModel by activityViewModels {
        AnimalViewModelFactory(
            (activity?.application as AnimalApplication).database.getAnimalDao()
        )
    }
    private val navigationArgs: AnimalDetailFragmentArgs by navArgs()
    lateinit var animal: Animal

    private var _binding: FragmentAddAnimalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddAnimalBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.name.text.toString(),
            binding.city.text.toString(),
            binding.desc.text.toString(),
            binding.contact.text.toString()
        )
    }
    private fun bind(animal: Animal) {
        binding.apply {
            name.setText(animal.yourName, TextView.BufferType.SPANNABLE)
            city.setText(animal.yourCity, TextView.BufferType.SPANNABLE)
            desc.setText(animal.animalDesc, TextView.BufferType.SPANNABLE)
            contact.setText(animal.contact, TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateAnimal() }
        }
    }
    private fun addNewAnimal() {
        if (isEntryValid()) {
            viewModel.addNewAnimal(
                binding.name.text.toString(),
                binding.city.text.toString(),
                binding.desc.text.toString(),
                binding.contact.text.toString()
            )
            val action = AddAnimalFragmentDirections.actionAddAnimalFragmentToAnimalListFragment()
            findNavController().navigate(action)
        }
    }
    private fun updateAnimal() {
        if (isEntryValid()) {
            viewModel.updateAnimal(
                this.navigationArgs.animalId,
                this.binding.name.text.toString(),
                this.binding.city.text.toString(),
                this.binding.desc.text.toString(),
                this.binding.contact.text.toString()
            )
            val action = AddAnimalFragmentDirections.actionAddAnimalFragmentToAnimalListFragment()
            findNavController().navigate(action)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.animalId
        if (id > 0) {
            viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
                animal = selectedItem
                bind(animal)
            }
        } else {
            binding.saveAction.setOnClickListener {
                addNewAnimal()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}