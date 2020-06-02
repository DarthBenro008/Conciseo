package com.benrostudios.conciseo.ui.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.benrostudios.conciseo.R
import com.benrostudios.conciseo.ui.base.ScopedFragment
import com.benrostudios.conciseo.util.shortToaster
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class Home : ScopedFragment() ,KodeinAware{

    override val kodein by closestKodein()
    private val viewModelFactory: HomeViewModelFactory by instance()


    companion object {
        fun newInstance() = Home()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)
        shortenUrl()
        shortenUrlResponse()
    }

    fun shortenUrl() = launch {
        viewModel.shortenUrl("www.google.com")
    }

    fun shortenUrlResponse() = launch {
        viewModel._shortenResponse.observe(viewLifecycleOwner, Observer {
            //shortToaster(it.toString())
            Log.d("URL Made", "$it")
        })
    }

}
