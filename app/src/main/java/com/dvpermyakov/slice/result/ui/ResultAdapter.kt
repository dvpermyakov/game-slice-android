package com.dvpermyakov.slice.result.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvpermyakov.slice.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_result.*

class ResultAdapter : RecyclerView.Adapter<ResultAdapter.MyViewHolder>() {

    var items: List<ResultItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_result, parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class MyViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: ResultItem) {
            val context = containerView.context

            imageView.post {
                Picasso.get()
                    .load(item.image)
                    .resize(
                        imageView.measuredWidth,
                        imageView.measuredHeight
                    )
                    .centerCrop()
                    .transform(RoundedCornersTransformation(32, 0))
                    .into(imageView)
            }
            if (item.isRight) {
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
}