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
                status = it.status,
                gender = it.gender,
                species = it.species,
                image = it.image,
                location = it.location.name,
                origin = it.origin.name,
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
                status = it.status,
                gender = it.gender,
                species = it.species,
                image = it.image,
                location = it.location,
                origin = it.origin,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Character) = CharacterEntity(
        characterId = input.characterId,
        name = input.name,
        status = input.status,
        gender = input.gender,
        species = input.species,
        image = input.image,
        location = input.location,
        origin = input.origin,
        isFavorite = input.isFavorite
    )

    fun mapResponseToEntity(input: CharacterResponse)=  CharacterEntity(
        characterId = input.id,
        name = input.name,
        status = input.status,
        gender = input.gender,
        species = input.species,
        image = input.image,
        location = input.location.name,
        origin = input.origin.name,
        isFavorite = false
    )

    fun mapEntityToDomain(input: CharacterEntity) = Character(
        characterId = input.characterId,
        name = input.name,
        status = input.status,
        gender = input.gender,
        species = input.species,
        image = input.image,
        location = input.location,
        origin = input.origin,
        isFavorite = input.isFavorite
    )


}