package com.kadabengaran.rickalmanac.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val characterId: Int,
    val name: String,
    val image: String,
    val isFavorite: Boolean
): Parcelable
