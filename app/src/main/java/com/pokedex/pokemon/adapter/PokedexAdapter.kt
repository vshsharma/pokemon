package com.pokedex.pokemon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.pokedex.data.entities.MasterDetail
import com.pokedex.domain.entities.PokemonDetailData
import com.pokedex.domain.entities.Types
import com.pokedex.pokemon.R
import com.pokedex.pokemon.util.Constants
import com.pokedex.pokemon.util.Constants.ID_FORMAT
import com.pokedex.pokemon.util.Utils
import com.pokedex.pokemon.util.capitalizeFirstCharacter
import com.pokedex.pokemon.util.loadImage

class PokedexAdapter(
    private val pokemonData: HashMap<String, MasterDetail>,
    private val onItemClick: OnItemClick
) :
    RecyclerView.Adapter<PokedexAdapter.ViewHolder>() {
    lateinit var getContext: Context
    var dataKey = pokemonData.keys.toList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokedexImage: ImageView = itemView.findViewById(R.id.pokedex_image)
        val pokedexName: TextView = itemView.findViewById(R.id.pokedex_name)
        val pokedexId: TextView = itemView.findViewById(R.id.pokedex_id)
        val parentView: ConstraintLayout = itemView.findViewById(R.id.item_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        getContext = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokedex_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataKey = dataKey[position]
        val itemData = pokemonData[dataKey]
        setImageBackground(holder.parentView, itemData?.pokemonDetailData)
        itemData?.pokemonDetailData?.name?.let {
            holder.pokedexName.text = itemData.pokemonDetailData?.name!!.capitalizeFirstCharacter()
        }
        holder.pokedexId.text = String.format(ID_FORMAT, itemData?.pokemonDetailData?.id)

        holder.pokedexImage.loadImage(
            "${Constants.BASE_IMAGE_URL}${itemData?.pokemonDetailData?.id}${Constants.IMG_FORMAT}",
            R.drawable.ic_pokemon
        )

        holder.parentView.setOnClickListener {
            onItemClick.onItemClicked(position, itemData?.pokemonDetailData?.name!!)
        }
    }

    private fun setImageBackground(
        gradientView: ConstraintLayout,
        pokemonDetailData: PokemonDetailData?,
    ) {
        val typesList = pokemonDetailData?.types?.map { types: Types -> types.type?.name }
        if (typesList != null) {
            gradientView.background = Utils().getGradientColors(
                getContext,
                typesList.toMutableList()
            )
        }
    }

    override fun getItemCount(): Int {
        return dataKey.size
    }

    interface OnItemClick {
        fun onItemClicked(position: Int, pokedexName: String)
    }
}