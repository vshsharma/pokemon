package com.pokedex.pokemon.ui.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.pokedex.data.entities.MasterDetail
import com.pokedex.pokemon.AnalyticsHelper
import com.pokedex.pokemon.R
import com.pokedex.pokemon.adapter.PokedexAdapter
import com.pokedex.pokemon.databinding.FragmentPokedexListBinding
import com.pokedex.pokemon.util.GridSpacingItemDecoration
import com.pokedex.pokemon.util.isLetters
import com.pokedex.pokemon.viewmodel.PokedexViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexListFragment : Fragment(), PokedexAdapter.OnItemClick {

    lateinit var binding: FragmentPokedexListBinding
    private lateinit var pokedexViewModel: PokedexViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentPokedexListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnalyticsHelper.logScreenLoadEvent("PokedexListFragment")

        pokedexViewModel = ViewModelProvider(requireActivity())[PokedexViewModel::class.java]

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.addItemDecoration(GridSpacingItemDecoration(2, 25, true))

        binding.inputSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                val result = actionId and EditorInfo.IME_MASK_ACTION
                when (result) {
                    EditorInfo.IME_ACTION_DONE -> {
                        if (view == null || (view.text?.isEmpty() ?: "") as Boolean) {
                            pokedexViewModel.resetData()
                        }
                        searchInList(view?.text)
                    }
                }
                return true
            }
        })

        pokedexViewModel.masterDetailsLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                val adapter = PokedexAdapter(it, this)
                binding.recyclerView.adapter = adapter
            }
            binding.loaderView.visibility = View.GONE
        }

        pokedexViewModel.receivedFiltereData.observe(viewLifecycleOwner) {
            if (it) {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.add(R.id.frame_container, FilterFragment())
                transaction.addToBackStack("FilterFragment")
                transaction.commit()
            }
        }

        binding.filterButton.setOnClickListener {
            pokedexViewModel.fetchFilterData()
        }
    }

    private fun searchInList(text: CharSequence?) {
        if (text.toString().isEmpty()) {
            return
        }
        if (text!!.isNotEmpty() && text.toString().isLetters()) {
            val dataKey = pokedexViewModel.masterDetailsLiveData.value
            val filteredData = HashMap<String, MasterDetail>()
            if (dataKey != null) {
                dataKey.forEach { (key, value) ->
                    if (key.contains(text, true)) {
                        filteredData.put(key, value)
                    }
                }
                pokedexViewModel.masterDetailsLiveData.value = filteredData
            }
        } else {
            val filteredData = HashMap<String, MasterDetail>()
            pokedexViewModel.masterDetailsLiveData.value?.forEach { (key, value) ->
                if (text.contains(value.pokemonDetailData?.id.toString(), true)) {
                    filteredData.put(key, value)
                }
            }
            pokedexViewModel.masterDetailsLiveData.value = filteredData
        }
    }

    override fun onItemClicked(position: Int, pokedexName: String) {

        AnalyticsHelper.logScreenLoadEvent("Item clicked")
        pokedexViewModel.selectedPokedexMutableLiveData.value = pokedexName

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame_container, PokedexDetailFragment())
        transaction.addToBackStack("PokedexDetailFragment")
        transaction.commit()
    }

}