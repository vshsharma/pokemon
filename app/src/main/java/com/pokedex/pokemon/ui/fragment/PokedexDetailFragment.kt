package com.pokedex.pokemon.ui.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pokedex.domain.entities.*
import com.pokedex.pokemon.R
import com.pokedex.pokemon.databinding.FragmentPokedexDetailBinding
import com.pokedex.pokemon.util.Constants
import com.pokedex.pokemon.util.Constants.ID_FORMAT
import com.pokedex.pokemon.util.Utils
import com.pokedex.pokemon.util.capitalizeFirstCharacter
import com.pokedex.pokemon.util.loadImage
import com.pokedex.pokemon.viewmodel.PokedexViewModel


class PokedexDetailFragment : Fragment() {

    lateinit var binding: FragmentPokedexDetailBinding
    private lateinit var pokedexViewModel: PokedexViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentPokedexDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokedexViewModel = ViewModelProvider(requireActivity())[PokedexViewModel::class.java]

        initializeDetailUI()
    }

    private fun initializeDetailUI() {
        pokedexViewModel.getPokemonDetail(pokedexViewModel.selectedPokedexMutableLiveData.value)

        binding.readMore.setOnClickListener {
            showPopUpWindow()
        }
        binding.closeButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        pokedexViewModel.pokemonDetailData.observe(viewLifecycleOwner) {
            handleSuccessResponse(it)
        }
        pokedexViewModel.pokemonAdditionalDetailData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.pokedexDescription.text = it.flavorTextEntries
                binding.eggGroupsValue.text = it.eggGroups.joinToString()
            }
        }
        pokedexViewModel.pokemonStrengthWeaknessData.observe(viewLifecycleOwner) {
            handleWeakness(it)
        }
        pokedexViewModel.evolutionData.observe(viewLifecycleOwner) {
            showEvolutionChain(it)
        }
        setCTAData()
    }

    private fun setCTAData() {
        val listPokedex: List<String> =
            pokedexViewModel.masterDetailsLiveData.value?.keys?.toList() ?: listOf()
        if (listPokedex.isNotEmpty()) {
            val index = listPokedex.indexOf(pokedexViewModel.selectedPokedexMutableLiveData.value)
            if (index > 1) {
                binding.previous.text = listPokedex[index - 1].capitalizeFirstCharacter()
                binding.previous.visibility = View.VISIBLE
            } else {
                binding.previous.visibility = View.INVISIBLE
            }

            if (index < listPokedex.size - 1) {
                binding.next.text = listPokedex[index + 1].capitalizeFirstCharacter()
                binding.next.visibility = View.VISIBLE
            } else {
                binding.next.visibility = View.INVISIBLE
            }

            binding.previous.setOnClickListener {
                pokedexViewModel.selectedPokedexMutableLiveData.value = listPokedex[index - 1]
                initializeDetailUI()

            }
            binding.next.setOnClickListener {
                pokedexViewModel.selectedPokedexMutableLiveData.value = listPokedex[index + 1]
                initializeDetailUI()
            }
        }
    }

    private fun showEvolutionChain(evolutionChainData: EvolutionChainData?) {
        binding.evolutionImage.removeAllViews()
        if (evolutionChainData != null) {
            for (i in 0 until  evolutionChainData.species.size) {
                val species = evolutionChainData.species[i]
                val types = getTypesData(species?.name)
                val typesList = types?.map { it -> it.type?.name }
                val layout =
                    LayoutInflater.from(requireContext()).inflate(R.layout.evolution_chain_view, null)
                val background = layout.findViewById<ConstraintLayout>(R.id.item_view)
                val pokedexImage = layout.findViewById<ImageView>(R.id.pokedex_image)
                val nextArrow = layout.findViewById<ImageView>(R.id.next_arrow)
                if(i < (evolutionChainData.species.size - 1)) {
                    nextArrow.visibility = View.VISIBLE
                }

                val imageId = species?.url?.replace("https://pokeapi.co/api/v2/pokemon-species/", "")
                    ?.replace("/", "")
                pokedexImage.loadImage("${Constants.BASE_IMAGE_URL}${imageId}${Constants.IMG_FORMAT}")
                if (typesList != null) {
                    background.background =
                        Utils().getGradientColors(requireContext(), typesList.toMutableList())
                }
                binding.evolutionImage.addView(layout)
            }
        }

    }

    private fun getTypesData(name: String?): List<Types>? {
        return pokedexViewModel.masterDetailsLiveData.value?.get(name)?.pokemonDetailData?.types
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showPopUpWindow() {
        val popupView: View =
            LayoutInflater.from(requireContext()).inflate(R.layout.popup_view, null)

        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // lets taps outside the popup also dismiss it
        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        popupView.setOnTouchListener { _, _ ->
            popupWindow.dismiss()
            true
        }
        popupView.findViewById<TextView>(R.id.description).text =
            pokedexViewModel.pokemonAdditionalDetailData.value?.flavorTextEntries
        popupView.findViewById<ImageButton>(R.id.close_button).setOnClickListener {
            popupWindow.dismiss()
        }
    }

    private fun handleWeakness(strengthWeaknessData: StrengthWeaknessData?) {
        binding.weakAgainstGroup.removeAllViews()
        if (strengthWeaknessData?.damageRelations != null) {
            for (data in strengthWeaknessData.damageRelations?.doubleDamageFrom!!) {
                val linearView: LinearLayout =
                    LayoutInflater.from(requireContext())
                        .inflate(R.layout.custom_text_view, null) as LinearLayout
                val typeView = linearView.findViewById<TextView>(R.id.child)
                typeView.text = data?.capitalizeFirstCharacter()
                typeView.setBackgroundResource(R.drawable.round_corners)
                val drawable: GradientDrawable = typeView.background as GradientDrawable
                data?.let { drawable.setColor(Utils.getTypesColor(it, requireContext())) }
                binding.weakAgainstGroup.addView(linearView)
            }
        }
    }

    private fun handleSuccessResponse(it: PokemonDetailData?) {
        binding.pokedexId.text = String.format(ID_FORMAT, it?.id)
        binding.pokedexName.text = it?.name
        binding.heightValue.text = it?.height.toString()
        binding.weightValue.text = it?.weight.toString()
        binding.abilitiesValue.text = it?.abilities?.map { result -> result.ability?.name }
            ?.toMutableList()
            ?.joinToString()
        it?.sprites?.frontDefault?.let { it1 ->
            binding.pokedexImage.loadImage(
                it1,
                R.drawable.ic_pokemon
            )
        }
        addTypesViews(it?.types?.map { result -> result.type?.name }
            ?.toMutableList())

        addStatics(it?.stats)


    }

    private fun addStatics(statsList: List<Stats>?) {
        binding.statsViewGroup.removeAllViews()
        if (statsList != null) {
            for (stats in statsList) {
                val linearView: ConstraintLayout =
                    LayoutInflater.from(requireContext())
                        .inflate(R.layout.stats_view, null) as ConstraintLayout
                val typeView = linearView.findViewById<TextView>(R.id.statType)
                val statsValue = linearView.findViewById<TextView>(R.id.statValue)
                val statsProgressBar = linearView.findViewById<ProgressBar>(R.id.statsProgressBar)

                typeView.text = stats.stat?.name
                statsValue.text = stats.baseStat.toString()
                stats.baseStat?.let { statsProgressBar.progress = it }
                binding.statsViewGroup.addView(linearView)
            }
        }
    }

    private fun addTypesViews(types: MutableList<String?>?) {
        binding.typesViewGroup.removeAllViews()
        if (types != null) {
            for (type in types) {
                val linearView: LinearLayout =
                    LayoutInflater.from(requireContext())
                        .inflate(R.layout.custom_text_view, null) as LinearLayout

                val typeView = linearView.findViewById<TextView>(R.id.child)
                typeView.text = type?.capitalizeFirstCharacter()
                typeView.setBackgroundResource(R.drawable.round_corners)
                val drawable: GradientDrawable = typeView.background as GradientDrawable
                type?.let { drawable.setColor(Utils.getTypesColor(it, requireContext())) }
                binding.typesViewGroup.addView(linearView)
            }
            setImageBackground(types)
        }
    }

    private fun setImageBackground(types: MutableList<String?>) {
        binding.imageBackground.background = Utils().getGradientColors(requireContext(), types)
    }


}