package com.benrostudios.conciseo.ui.history

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.benrostudios.conciseo.R
import com.benrostudios.conciseo.adapters.HistoryAdapter
import com.benrostudios.conciseo.ui.base.ScopedFragment
import com.benrostudios.conciseo.util.appear
import com.benrostudios.conciseo.util.hide
import com.benrostudios.conciseo.util.shortToaster
import kotlinx.android.synthetic.main.history_fragment.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class History : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: HistoryViewModelFactory by instance()
    private lateinit var adapter: HistoryAdapter

    companion object {
        fun newInstance() = History()
    }

    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.history_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HistoryViewModel::class.java)
        history_recycler.layoutManager = LinearLayoutManager(context)
        fetchHistory()
    }

    private fun fetchHistory() = launch {
        viewModel.fetchHistory()
        viewModel.fetchedHistory.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                history_recycler.appear()
                history_placeholder.hide()
                history_placeholder_text.hide()
                adapter = HistoryAdapter(it)
                history_recycler.adapter = adapter
            }else{
                history_recycler.hide()
                history_placeholder.appear()
                history_placeholder_text.appear()
            }
        })
    }

}
