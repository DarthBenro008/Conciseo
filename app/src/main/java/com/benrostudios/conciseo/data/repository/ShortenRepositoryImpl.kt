package com.benrostudios.conciseo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.conciseo.data.db.HistoryDAO
import com.benrostudios.conciseo.data.models.ShortnerResponse
import com.benrostudios.conciseo.data.models.ShortnerResult
import com.benrostudios.conciseo.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShortenRepositoryImpl(
    private val apiService: ApiService,
    private val historyDAO: HistoryDAO
) : ShortenRepository, BaseRepository() {

    private val _shortnerResponse = MutableLiveData<ShortnerResponse>()

    override val networkError: LiveData<String>
        get() = _networkErrorResolution

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

    override suspend fun upsertItem(item: ShortnerResult) {
        historyDAO.upsert(item)
    }

    override suspend fun fetchHistory(): LiveData<List<ShortnerResult>> {
        return withContext(Dispatchers.IO){
            return@withContext historyDAO.getHistory()
        }
    }

    override suspend fun deleteHistoryItem(item: ShortnerResult) {
        historyDAO.deleteHistory(item)
    }


}