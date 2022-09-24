package com.kadabengaran.rickalmanac.di

import com.kadabengaran.rickalmanac.core.domain.usecase.CharacterUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun characterUseCase(): CharacterUseCase
}