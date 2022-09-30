package com.kadabengaran.rickalmanac.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val characterId: Int,
    val name: String,
    val status: String,
    val gender: String,
    val species: String,
    val image: String,
    val origin: String,
    val location: String,
    val isFavorite: Boolean
) : Parcelable
