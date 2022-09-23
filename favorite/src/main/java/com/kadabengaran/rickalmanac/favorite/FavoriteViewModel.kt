package com.kadabengaran.rickalmanac.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kadabengaran.rickalmanac.core.domain.usecase.CharacterUseCase

class FavoriteViewModel(characterUseCase: CharacterUseCase): ViewModel() {
    val character = characterUseCase.getFavoriteCharacter().asLiveData()
}