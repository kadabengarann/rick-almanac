package com.kadabengaran.rickalmanac.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "characterId")
    var characterId: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "gender")
    var gender: String,

    @ColumnInfo(name = "species")
    var species: String,

    @ColumnInfo(name = "location")
    var location: String,

    @ColumnInfo(name = "origin")
    var origin: String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)
