package com.kadabengaran.rickalmanac.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadabengaran.rickalmanac.core.data.Resource
import com.kadabengaran.rickalmanac.core.domain.model.Character
import com.kadabengaran.rickalmanac.core.domain.usecase.CharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCharacterViewModel @Inject constructor(private val characterUseCase: CharacterUseCase) : ViewModel() {

    private val _detailCharacter = MutableLiveData<Resource<Character>>()
    val detailCharacter: LiveData<Resource<Character>> = _detailCharacter

    fun getDetailCharacter(id: Int) {
        viewModelScope.launch {
            characterUseCase.getDetailCharacter(id).collect(_detailCharacter::postValue)
        }
    }

    fun setFavoriteCharacter(character: Character, newStatus: Boolean) = characterUseCase.setFavoriteCharacter(character, newStatus)
}
