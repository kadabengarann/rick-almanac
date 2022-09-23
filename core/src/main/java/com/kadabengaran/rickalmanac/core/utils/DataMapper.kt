package com.kadabengaran.rickalmanac.core.utils

import com.kadabengaran.rickalmanac.core.data.source.local.entity.CharacterEntity
import com.kadabengaran.rickalmanac.core.data.source.remote.response.CharacterResponse
import com.kadabengaran.rickalmanac.core.domain.model.Character

object DataMapper {
    fun mapResponsesToEntities(input: List<CharacterResponse>): List<CharacterEntity> {
        val characterList = ArrayList<CharacterEntity>()
        input.map {
            val character = CharacterEntity(
                characterId = it.id,
                name = it.name,
                image = it.image,
                isFavorite = false
            )
            characterList.add(character)
        }
        return characterList
    }

    fun mapEntitiesToDomain(input: List<CharacterEntity>): List<Character> =
        input.map {
            Character(
                characterId = it.characterId,
                name = it.name,
                image = it.image,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Character) = CharacterEntity(
        characterId = input.characterId,
        name = input.name,
        image = input.image,
        isFavorite = input.isFavorite
    )
}