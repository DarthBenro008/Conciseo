package com.benrostudios.conciseo.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benrostudios.conciseo.data.models.ShortnerResponse
import com.benrostudios.conciseo.data.models.ShortnerResult
import com.benrostudios.conciseo.data.repository.ShortenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val shortenRepository: ShortenRepository
) : ViewModel() {
    val _shortenResponse = MutableLiveData<ShortnerResponse>()
    val networkError = MutableLiveData<String>()


    init {
        shortenRepository.shortenResponse.observeForever {
            _shortenResponse.postValue(it)
        }
         shortenRepository.networkError.observeForever {
             networkError.postValue(it)
         }
    }

    suspend fun shortenUrl(url: String){
        shortenRepository.shortenCall(url)
    }

    suspend fun upsertItem(item: ShortnerResult){
        viewModelScope.launch(Dispatchers.IO) {
            shortenRepository.upsertItem(item)
        }

    }
}
