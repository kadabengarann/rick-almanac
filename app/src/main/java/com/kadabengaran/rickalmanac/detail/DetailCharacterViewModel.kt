package com.kadabengaran.rickalmanac.detail

import androidx.lifecycle.ViewModel
import com.kadabengaran.rickalmanac.core.domain.model.Character
import com.kadabengaran.rickalmanac.core.domain.usecase.CharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailCharacterViewModel @Inject constructor(private val characterUseCase: CharacterUseCase) : ViewModel() {
    fun setFavoriteCharacter(character: Character, newStatus:Boolean) = characterUseCase.setFavoriteCharacter(character, newStatus)

}