package com.benrostudios.conciseo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.conciseo.data.repository.ShortenRepository


@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val shortenRepository: ShortenRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(shortenRepository) as T
    }
}