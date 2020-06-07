package com.benrostudios.conciseo.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.benrostudios.conciseo.R
import com.benrostudios.conciseo.ui.base.ScopedFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class About : ScopedFragment(),KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: AboutViewModelFactory by instance()

    companion object {
        fun newInstance() = About()
    }

    private lateinit var viewModel: AboutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.about_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(AboutViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
