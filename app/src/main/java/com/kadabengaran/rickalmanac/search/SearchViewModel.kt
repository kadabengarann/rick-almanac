package com.kadabengaran.rickalmanac.search

import androidx.lifecycle.*
import com.kadabengaran.rickalmanac.core.data.Resource
import com.kadabengaran.rickalmanac.core.domain.model.Character
import com.kadabengaran.rickalmanac.core.domain.usecase.CharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val characterUseCase: CharacterUseCase) : ViewModel() {
    private val _listSearchResult = MutableLiveData<Resource<List<Character>>>()
    val listSearchResult: LiveData<Resource<List<Character>>> = _listSearchResult

    private val _queryValue = MutableLiveData<String>()
    val queryValue: LiveData<String> = _queryValue

    fun setQuery(query: String) {
        _queryValue.value = query
    }

    fun searchUser(query: String) {
        viewModelScope.launch {
            characterUseCase.searchCharacter(query).collect {
                _listSearchResult.postValue(it)
            }
        }
    }
}