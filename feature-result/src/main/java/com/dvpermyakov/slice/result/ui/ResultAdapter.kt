package com.dvpermyakov.slice.result.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvpermyakov.slice.result.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_result.*
import kotlinx.android.synthetic.main.layout_result_header.*

class ResultAdapter : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    var header: ResultHeader? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var items: List<ResultItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            if (viewType == VIEW_TYPE_ITEM) {
                R.layout.layout_result
            } else {
                R.layout.layout_result_header
            },
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ITEM) {
            holder.bindItem(items[position - 1])
        } else if (holder.itemViewType == VIEW_TYPE_HEADER) {
            header?.let { header ->
                holder.bindHeader(header)
            }
        }
    }

    class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindHeader(header: ResultHeader) {
            headerTitleView.text = header.title
            headerDescriptionView.text = header.description
        }

        fun bindItem(item: ResultItem) {
            val context = containerView.context
            imageView.setImageBitmap(item.image)

            if (item.isCorrect) {
                rightTextView.text = context.getString(R.string.result_right)
                rightTextView.background = context.getDrawable(R.drawable.shape_right)
            } else {
                rightTextView.text = context.getString(R.string.result_wrong)
                rightTextView.background = context.getDrawable(R.drawable.shape_wrong)
            }

            titleView.text = item.title
            descriptionView.text = item.description
            if (item.isDescriptionCrossed) {
                descriptionView.paintFlags =
                    descriptionView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            extraDescriptionView.text = item.extraDescription
            extraDescriptionView.visibility = if (item.extraDescription != null) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_ITEM = 1
    }
}