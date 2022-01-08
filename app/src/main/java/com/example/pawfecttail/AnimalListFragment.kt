package com.example.pawfecttail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pawfecttail.databinding.FragmentAnimalListBinding

class AnimalListFragment : Fragment() {
    private val viewModel: AnimalViewModel by activityViewModels {
            AnimalViewModelFactory(
                (activity?.application as AnimalApplication).database.getAnimalDao()
            )
    }

    private var _binding: FragmentAnimalListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimalListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AnimalRVAdapter {
            val action =
                AnimalListFragmentDirections.actionAnimalListFragmentToAnimalDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.RVanimal.layoutManager = LinearLayoutManager(this.context)
        binding.RVanimal.adapter = adapter
        // Attach an observer on the allItems list to update the UI automatically when the data
        // changes.
        viewModel.allAnimals.observe(this.viewLifecycleOwner) { animals ->
            animals.let {
                adapter.submitList(it)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val action = AnimalListFragmentDirections.actionAnimalListFragmentToAddAnimalFragment(
            getString(R.string.add_fragment_title))
            this.findNavController().navigate(action)
        }
    }
}
