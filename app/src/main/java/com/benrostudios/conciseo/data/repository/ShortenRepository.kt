package com.benrostudios.conciseo.data.repository

import androidx.lifecycle.LiveData
import com.benrostudios.conciseo.data.models.ShortnerResponse
import com.benrostudios.conciseo.data.models.ShortnerResult

interface ShortenRepository {
    suspend fun shortenCall(url: String)
    suspend fun upsertItem(item: ShortnerResult)
    suspend fun fetchHistory(): LiveData<List<ShortnerResult>>
    suspend fun deleteHistoryItem(item: ShortnerResult)
    val networkError : LiveData<String>
    val shortenResponse: LiveData<ShortnerResponse>
}