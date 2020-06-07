package com.benrostudios.conciseo.adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.conciseo.R
import com.benrostudios.conciseo.data.models.ShortnerResult
import com.benrostudios.conciseo.util.shortToaster
import kotlinx.android.synthetic.main.history_item.view.*

class HistoryAdapter(
    val historyItems: List<ShortnerResult>
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        mContext = parent.context
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int = historyItems.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.linkTitle.text = historyItems[position].originalLink
        holder.time.text = historyItems[position].fullShortLink
        holder.container.setOnClickListener {
            mContext.shortToaster("${historyItems[position].shortLink}")
        }
    }


    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val linkTitle: TextView = view.item_link
        val time: TextView = view.item_time
        val container: CardView = view.item_holder
    }
}