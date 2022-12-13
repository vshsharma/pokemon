package com.pokedex.pokemon.util

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.annotation.RequiresApi
import com.pokedex.pokemon.R

class Utils {

        @RequiresApi(Build.VERSION_CODES.M)
        fun getGradient(colorsArray: MutableList<Int>): GradientDrawable {
            return GradientDrawable().apply {
                colors = colorsArray.toIntArray()
                orientation = GradientDrawable.Orientation.TOP_BOTTOM
                gradientType = GradientDrawable.LINEAR_GRADIENT
                shape = GradientDrawable.RECTANGLE
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getGradientColors(requireContext: Context, types: MutableList<String?>): GradientDrawable {
        val colorsList: MutableList<Int> = ArrayList()
        for (type in types) {
            type?.let { colorsList.add(getTypesColor(type, requireContext)) }
        }
        if(colorsList.size < 2) {
            colorsList.add(colorsList[0])
        }
       return getGradient(colorsList)
    }

    companion object {

        @RequiresApi(Build.VERSION_CODES.M)
        fun getTypesColor(type: String, context: Context): Int{
            if(type.equals("firing", true)) {
                return context.resources.getColor(R.color.fighting, null)
            } else if(type.equals("normal", true)) {
                return context.resources.getColor(R.color.normal, null)
            }else if(type.equals("flying", true)) {
                return context.resources.getColor(R.color.flying, null)
            }else if(type.equals("poison", true)) {
                return context.resources.getColor(R.color.poison, null)
            }else if(type.equals("ground", true)) {
                return context.resources.getColor(R.color.ground, null)
            }else if(type.equals("rock", true)) {
                return context.resources.getColor(R.color.rock, null)
            }else if(type.equals("bug", true)) {
                return context.resources.getColor(R.color.bug, null)
            }else if(type.equals("ghost", true)) {
                return context.resources.getColor(R.color.ghost, null)
            }else if(type.equals("steel", true)) {
                return context.resources.getColor(R.color.steel, null)
            }else if(type.equals("fire", true)) {
                return context.resources.getColor(R.color.fire, null)
            }else if(type.equals("water", true)) {
                return context.resources.getColor(R.color.water, null)
            }else if(type.equals("grass", true)) {
                return context.resources.getColor(R.color.grass, null)
            }else if(type.equals("electric", true)) {
                return context.resources.getColor(R.color.electric, null)
            }else if(type.equals("psychic", true)) {
                return context.resources.getColor(R.color.psychic, null)
            }else if(type.equals("ice", true)) {
                return context.resources.getColor(R.color.ice, null)
            }else if(type.equals("dragon", true)) {
                return context.resources.getColor(R.color.dragon, null)
            }else if(type.equals("dark", true)) {
                return context.resources.getColor(R.color.dark, null)
            }else if(type.equals("fairy", true)) {
                return context.resources.getColor(R.color.fairy, null)
            }else if(type.equals("unknown", true)) {
                return context.resources.getColor(R.color.unknown, null)
            }else if(type.equals("shadow", true)) {
                return context.resources.getColor(R.color.shadow, null)
            } else {
                return context.resources.getColor(R.color.normal, null)
            }

        }
    }
}