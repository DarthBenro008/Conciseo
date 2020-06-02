package com.benrostudios.conciseo.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.conciseo.data.models.ShortnerResponse
import com.benrostudios.conciseo.data.repository.ShortenRepository

class HomeViewModel(
    private val shortenRepository: ShortenRepository
) : ViewModel() {
    val _shortenResponse = MutableLiveData<ShortnerResponse>()

    init {
        shortenRepository.shortenResponse.observeForever {
            _shortenResponse.postValue(it)
        }
    }

    suspend fun shortenUrl(url: String){
        shortenRepository.shortenCall(url)
    }
}
