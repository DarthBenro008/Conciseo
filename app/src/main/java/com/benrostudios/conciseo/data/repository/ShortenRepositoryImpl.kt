package com.benrostudios.conciseo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.conciseo.data.models.ShortnerResponse
import com.benrostudios.conciseo.data.network.ApiService

class ShortenRepositoryImpl(
    private val apiService: ApiService
) : ShortenRepository, BaseRepository() {

    private val _shortnerResponse = MutableLiveData<ShortnerResponse>()

    override val shortenResponse: LiveData<ShortnerResponse>
        get() = _shortnerResponse

    override suspend fun shortenCall(url: String) {
        _shortnerResponse.postValue(
            safeApiCall(
                call = { apiService.shortenLink(url) },
                error = "Error Shortening URL"
            )
        )
    }
}