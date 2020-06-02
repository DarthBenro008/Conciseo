package com.benrostudios.conciseo.data.repository

import androidx.lifecycle.LiveData
import com.benrostudios.conciseo.data.models.ShortnerResponse

interface ShortenRepository {
    suspend fun shortenCall(url: String)
    val shortenResponse: LiveData<ShortnerResponse>
}