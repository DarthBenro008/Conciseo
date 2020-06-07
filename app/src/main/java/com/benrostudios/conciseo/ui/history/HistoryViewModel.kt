package com.benrostudios.conciseo.ui.history

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.conciseo.data.models.ShortnerResult
import com.benrostudios.conciseo.data.repository.ShortenRepository

class HistoryViewModel(
    private val shortenRepository: ShortenRepository
) : ViewModel() {
    val fetchedHistory = MutableLiveData<List<ShortnerResult>>()

    suspend fun fetchHistory(){
        shortenRepository.fetchHistory().observeForever{
            Log.d("test","${it}")
            fetchedHistory.postValue(it)
        }
    }
}
