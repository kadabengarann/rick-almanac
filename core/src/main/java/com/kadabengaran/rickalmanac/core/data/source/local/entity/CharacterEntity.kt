package com.kadabengaran.rickalmanac.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "characterId")
    var characterId: Int,

    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)
