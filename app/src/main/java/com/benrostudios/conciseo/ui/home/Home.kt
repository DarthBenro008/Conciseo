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
import com.benrostudios.conciseo.data.models.ShortnerResult
import com.benrostudios.conciseo.ui.base.HeavyScopedFragment
import com.benrostudios.conciseo.ui.base.ScopedFragment
import com.benrostudios.conciseo.util.isValidURL
import com.benrostudios.conciseo.util.shortToaster
import kotlinx.android.synthetic.main.home_fragment.*
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
        trigger.setOnClickListener {
            if(url_input.text.isValidURL()){
                shortenUrl(url_input.text.toString())
                shortenUrlResponse()
            }else{
                shortToaster("Enter a Valid URL")
            }
        }

    }

    private fun shortenUrl(url: String) = launch {
        viewModel.shortenUrl(url)
    }

    private fun shortenUrlResponse() = launch {
        viewModel._shortenResponse.observe(viewLifecycleOwner, Observer {
            Log.d("URL Made", "$it")
            shortToaster("$it")
            it.result.time = 123
            upsertItem(it.result)
        })
    }

    private fun upsertItem(item: ShortnerResult) = launch {
        viewModel.upsertItem(item)
    }

}
