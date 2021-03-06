package com.benrostudios.conciseo.ui.home

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.conciseo.R
import com.benrostudios.conciseo.data.models.ShortnerResult
import com.benrostudios.conciseo.ui.base.ScopedFragment
import com.benrostudios.conciseo.util.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*


class Home : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: HomeViewModelFactory by instance()
    private var pasteData: String = ""


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
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        trigger.setOnClickListener {
            if (url_input.text.isValidURL()) {
                trigger.isClickable = false
                progressBar.appear()
                shortenUrl(url_input.text.toString())
                shortenUrlResponse()
            } else {
                url_input.error = "Enter a Valid URL!"
            }
        }

        link_result_display.setOnClickListener {
            val clipboard: ClipboardManager? =
                activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("link", link_result_display.text.toString())
            clipboard?.setPrimaryClip(clip)
            shortToaster("Link Copied to Clipboard!")
        }

        alternative_link_result_display.setOnClickListener {
            val clipboard: ClipboardManager? =
                activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip =
                ClipData.newPlainText("link", alternative_link_result_display.text.toString())
            clipboard?.setPrimaryClip(clip)
            shortToaster("Link Copied to Clipboard!")
        }

        clipChecker()

    }


    private fun shortenUrl(url: String) = launch {
        viewModel.shortenUrl(url)
    }

    private fun shortenUrlResponse() = launch {
        viewModel.networkError.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                if (it == "Unresolved Host" && context?.isConnected() == false) {
                    trigger.errorSnackbar("Check Your Internet Connectivity!")
                } else {
                    trigger.errorSnackbar(it)
                }
                progressBar.hide()
                trigger.isClickable = true
            }
        })
        viewModel._shortenResponse.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Log.d("URL Made", "$it")
                progressBar.hide()
                it.result.time = 123
                upsertItem(it.result)
                populateUI(it.result)
                trigger.isClickable = true
                placeholder_image.hide()
                placeholder_text.hide()
                result_container.appear()
            }
        })
    }

    private fun upsertItem(item: ShortnerResult) = launch {
        viewModel.upsertItem(item)
    }

    @SuppressLint("SimpleDateFormat")
    private fun populateUI(item: ShortnerResult) {
        link_result_display.text = item.fullShortLink
        alternative_link_result_display.text = item.fullShortLink2
        val currentTime: Date = Calendar.getInstance().time
        val sdf = SimpleDateFormat("hh:mm ")
        time_generation_display.text = "Link Generated Time : ${sdf.format(currentTime)}"
    }

    private fun clipChecker() {
        var clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var clipText = when {
            !clipboard.hasPrimaryClip() -> {
                false
            }
            !(clipboard.primaryClipDescription?.hasMimeType(MIMETYPE_TEXT_PLAIN))!! -> {
                // This disables the paste menu item, since the clipboard has data but it is not plain text
                false
            }
            else -> {
                // This enables the paste menu item, since the clipboard contains plain text.
                true
            }
        }
        if(clipText){
            var snackbar = Snackbar.make(trigger,"Paste recently copied website",Snackbar.LENGTH_LONG)
            snackbar.setAction("Paste") {
                fetchFromClipboard()
                if(pasteData.isNotEmpty())
                    url_input.setText(pasteData)
            }
            snackbar.show()
        }
    }

    private fun fetchFromClipboard(){
        var clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val item = clipboard.primaryClip?.getItemAt(0)
        pasteData = item?.text.toString()
        Log.d("Home","$pasteData")

    }

}
