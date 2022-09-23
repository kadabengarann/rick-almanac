package com.kadabengaran.rickalmanac.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kadabengaran.rickalmanac.core.data.source.local.entity.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class CharacterDatabase: RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}