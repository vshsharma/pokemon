package com.pokedex.pokemon.ui.fragment

import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.pokedex.pokemon.R
import com.pokedex.pokemon.adapter.FilterAdapter
import com.pokedex.pokemon.adapter.GenderFilterAdapter
import com.pokedex.pokemon.databinding.FragmentFilterBinding
import com.pokedex.pokemon.util.GridSpacingItemDecoration
import com.pokedex.pokemon.viewmodel.PokedexViewModel
import java.util.HashSet

class FilterFragment : Fragment(), FilterAdapter.TypeItemClickListener,
    GenderFilterAdapter.GenderItemClickListener {
    lateinit var binding: FragmentFilterBinding
    private lateinit var pokedexViewModel: PokedexViewModel
    private var genderSelect = HashSet<String>()
    private var typeSelect = HashSet<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokedexViewModel = ViewModelProvider(requireActivity())[PokedexViewModel::class.java]
        if(pokedexViewModel.genderLiveData.value != null){
            genderSelect = pokedexViewModel.genderLiveData.value!!
            displaySelectedFilters()
        }
        if(pokedexViewModel.typeSelectLiveData.value != null) {
            typeSelect = pokedexViewModel.typeSelectLiveData.value!!
            displaySelectedFilters()
        }

        handleTypesFilterUI()

        handleGenderFilterUI()

        binding.closeButton.setOnClickListener { requireActivity().onBackPressed() }
        binding.applyButton.setOnClickListener {
            pokedexViewModel.genderLiveData.value = genderSelect
            pokedexViewModel.typeSelectLiveData.value = typeSelect
            displaySelectedFilters()

            pokedexViewModel.applyFilters()
            requireActivity().onBackPressed()
        }
        binding.resetButton.setOnClickListener {
            genderSelect.clear()
            typeSelect.clear()
            pokedexViewModel.genderLiveData.value = genderSelect
            pokedexViewModel.typeSelectLiveData.value = typeSelect

            binding.genderFilter.selectedFilter.text = ""
            binding.typesFilter.selectedFilter.text = ""
            pokedexViewModel.resetData()
        }

    }

    private fun displaySelectedFilters() {
        if (genderSelect.isNotEmpty()) {
            val spannableGender =
                SpannableString("${genderSelect.last()} + ${genderSelect.size - 1} more")
            spannableGender.setSpan(
                StyleSpan(BOLD),
                genderSelect.last().length + 2, spannableGender.length - 2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.genderFilter.selectedFilter.text = spannableGender
        }
        if (typeSelect.isNotEmpty()) {
            println("${typeSelect.last().length}")
            val spannableType =
                SpannableString("(${typeSelect.last()} + ${typeSelect.size - 1} more)")
            spannableType.setSpan(
                StyleSpan(BOLD),
                typeSelect.last().length + 2, spannableType.length - 2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.typesFilter.selectedFilter.text = spannableType
        }
    }

    /**
     * This method will handle UI for gender filter
     */
    private fun handleGenderFilterUI() {
        binding.genderFilter.filterHeader.text = requireContext().getString(R.string.gender)
        binding.genderFilter.expandCollapse.setOnClickListener {
            if (binding.genderFilter.hiddenView.visibility == View.GONE) {
                binding.genderFilter.hiddenView.visibility = View.VISIBLE
                binding.genderFilter.expandCollapse.setImageResource(R.drawable.ic_collapse)
            } else {
                binding.genderFilter.hiddenView.visibility = View.GONE
                binding.genderFilter.expandCollapse.setImageResource(R.drawable.ic_expand)
            }
        }

        pokedexViewModel.genderFilterData.observe(viewLifecycleOwner) {
            binding.genderFilter.filtersRecyclerview.layoutManager =
                GridLayoutManager(requireContext(), 2)
            binding.genderFilter.filtersRecyclerview.addItemDecoration(
                GridSpacingItemDecoration(
                    2,
                    40,
                    true
                )
            )
            val adapter =
                it?.let { it1 -> GenderFilterAdapter(it1.genderResult, this, genderSelect) }
            binding.genderFilter.filtersRecyclerview.adapter = adapter
        }
    }

    /**
     * This method will handle UI for type filter
     */
    private fun handleTypesFilterUI() {
        pokedexViewModel.typeFilterData.observe(viewLifecycleOwner) {
            binding.typesFilter.filtersRecyclerview.layoutManager =
                GridLayoutManager(requireContext(), 2)
            binding.typesFilter.filtersRecyclerview.addItemDecoration(
                GridSpacingItemDecoration(
                    2,
                    40,
                    true
                )
            )
            val adapter = it?.let { it1 -> FilterAdapter(it1.typeFilters, this, typeSelect) }
            binding.typesFilter.filtersRecyclerview.adapter = adapter
        }
        binding.typesFilter.filterHeader.text = requireContext().getString(R.string.type)

        binding.typesFilter.expandCollapse.setOnClickListener {
            if (binding.typesFilter.hiddenView.visibility == View.GONE) {
                binding.typesFilter.hiddenView.visibility = View.VISIBLE
                binding.typesFilter.expandCollapse.setImageResource(R.drawable.ic_collapse)
            } else {
                binding.typesFilter.hiddenView.visibility = View.GONE
                binding.typesFilter.expandCollapse.setImageResource(R.drawable.ic_expand)
            }
        }
    }

    override fun onTypeItemClicked(position: Int, filterName: String, isChecked: Boolean) {
        if (isChecked) {
            typeSelect.add(filterName)
        } else {
            typeSelect.remove(filterName)
        }

    }

    override fun onGenderItemClicked(position: Int, genderName: String, isChecked: Boolean) {
        if (isChecked) {
            genderSelect.add(genderName)
        } else {
            genderSelect.remove(genderName)
        }
    }

}