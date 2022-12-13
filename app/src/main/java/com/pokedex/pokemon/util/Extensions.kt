package com.pokedex.pokemon.util

import android.widget.ImageView
import androidx.annotation.NonNull
import com.pokedex.pokemon.R
import com.squareup.picasso.Picasso

fun ImageView.loadImage(
    url: String = "",
    @NonNull placeHolderDrawable: Int = R.mipmap.ic_launcher,
) {
    if (url.isNotEmpty())
        Picasso.get().load(url).placeholder(placeHolderDrawable).into(this)

//    SvgLoader.pluck()
//        .with(this)
//        .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
//        .load(url, image);
}

fun String.isLetters(): Boolean {
    return this.matches("^[a-zA-Z]*$".toRegex())
}
fun String.capitalizeFirstCharacter() : String {
    return this
        .substring(0, 1).uppercase() + this.substring(1)
}