package com.pokedex.pokemon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pokedex.pokemon.R
import com.pokedex.pokemon.util.capitalizeFirstCharacter
import java.util.HashSet

class FilterAdapter(
    val typeFilters: List<String?>,
    val typeItemClickListener: TypeItemClickListener,
    val typeSelect: HashSet<String>
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    class FilterViewHolder(item: View): RecyclerView.ViewHolder(item) {
        var filterName: TextView = item.findViewById(R.id.filterName)
        var checkBox: CheckBox = item.findViewById(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false)
        return FilterViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.filterName.text = typeFilters[position]?.capitalizeFirstCharacter()
        holder.checkBox.isChecked = typeSelect.contains(typeFilters[position])
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            typeFilters[position]?.let {
                typeItemClickListener.onTypeItemClicked(
                    position,
                    it,
                    isChecked
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return typeFilters.size
    }

    interface TypeItemClickListener {
        fun onTypeItemClicked(position: Int, filterName: String, isChecked: Boolean)
    }
}