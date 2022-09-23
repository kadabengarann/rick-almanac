package com.kadabengaran.rickalmanac.di

import com.kadabengaran.rickalmanac.core.domain.usecase.CharacterInteractor
import com.kadabengaran.rickalmanac.core.domain.usecase.CharacterUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideCharacterUseCase(characterInteractor: CharacterInteractor): CharacterUseCase
}