package com.dvpermyakov.slice.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvpermyakov.slice.R
import com.dvpermyakov.slice.presentation.GameState
import com.dvpermyakov.slice.presentation.GameViewModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(GameViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getGameState().observe(viewLifecycleOwner, Observer { state ->
            when(state) {
                GameState.Loading -> TODO()
                is GameState.NextCard -> {
                    descriptionView.text = state.title
                    leftPicker.text = state.leftTitle
                    rightPicker.text = state.rightTitle

                    val card = state.currentCard
                    cardImageView.post {
                        Picasso.get()
                            .load(card.image)
                            .resize(cardImageView.measuredWidth, cardImageView.measuredHeight)
                            .centerCrop()
                            .transform(RoundedCornersTransformation(32, 0))
                            .into(cardImageView)
                    }
                    cardTitleView.text = card.name
                }
            }
        })
    }

    companion object {
        fun newInstance() = GameFragment()
    }
}