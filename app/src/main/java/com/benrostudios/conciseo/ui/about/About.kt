package com.benrostudios.conciseo.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.benrostudios.conciseo.R
import com.benrostudios.conciseo.ui.base.ScopedFragment
import com.benrostudios.conciseo.util.shortToaster
import kotlinx.android.synthetic.main.about_fragment.*
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
        github.setOnClickListener {
            val githubUrlIntent = Intent(Intent.ACTION_VIEW)
            githubUrlIntent.data = Uri.parse(resources.getString(R.string.github_url))
            startActivity(githubUrlIntent)
        }
        email.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(resources.getString(R.string.email_id)))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Feedback")
            if (emailIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(emailIntent)
            }else{
                shortToaster("No Email apps found!")
            }
        }
        contribution_text.setOnClickListener {
            val projectUrlIntent = Intent(Intent.ACTION_VIEW)
            projectUrlIntent.data = Uri.parse(resources.getString(R.string.project_url))
            if (projectUrlIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(projectUrlIntent)
            }else{
                shortToaster("No Email apps found!")
            }
        }
    }

}
