package com.kadabengaran.rickalmanac.core.di

import android.content.Context
import androidx.room.Room
import com.kadabengaran.rickalmanac.core.data.source.local.room.CharacterDao
import com.kadabengaran.rickalmanac.core.data.source.local.room.CharacterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CharacterDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("rickAlmanac".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            CharacterDatabase::class.java, "Character.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory).build()
    }

    @Provides
    fun provideCharacterDao(database: CharacterDatabase): CharacterDao = database.characterDao()
}
