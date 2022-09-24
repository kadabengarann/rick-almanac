package com.kadabengaran.rickalmanac.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kadabengaran.rickalmanac.core.domain.usecase.CharacterUseCase

class FavoriteViewModel(characterUseCase: CharacterUseCase): ViewModel() {
    val favoriteCharacter = characterUseCase.getFavoriteCharacter().asLiveData()
}