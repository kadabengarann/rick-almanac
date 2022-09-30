package com.kadabengaran.rickalmanac.core.di

import com.kadabengaran.rickalmanac.core.data.CharacterRepository
import com.kadabengaran.rickalmanac.core.domain.repository.ICharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(characterRe: CharacterRepository): ICharacterRepository
}
