package com.kadabengaran.rickalmanac.core.domain.usecase

import com.kadabengaran.rickalmanac.core.data.Resource
import com.kadabengaran.rickalmanac.core.domain.model.Character
import com.kadabengaran.rickalmanac.core.domain.repository.ICharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterInteractor @Inject constructor(private val characterRepository: ICharacterRepository): CharacterUseCase {

    override fun getAllCharacter() = characterRepository.getAllCharacter()

    override fun getFavoriteCharacter() = characterRepository.getFavoriteCharacter()

    override fun setFavoriteCharacter(character: Character, state: Boolean) = characterRepository.setFavoriteCharacter(character, state)

    override suspend fun searchCharacter(query: String) = characterRepository.searchCharacter(query)
}