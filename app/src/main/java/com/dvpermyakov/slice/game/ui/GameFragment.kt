package com.dvpermyakov.slice.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dvpermyakov.slice.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardImageView.post {
            Picasso.get()
                .load(R.drawable.eqw)
                .resize(cardImageView.measuredWidth, cardImageView.measuredHeight)
                .centerCrop()
                .transform(RoundedCornersTransformation(32, 0))
                .into(cardImageView)
        }
    }

    companion object {
        fun newInstance() = GameFragment()
    }
}