package com.dvpermyakov.slice.game.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dvpermyakov.slice.R
import com.dvpermyakov.slice.game.presentation.GameState
import com.dvpermyakov.slice.game.presentation.GameViewModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_game.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class GameFragment : Fragment(), KodeinAware {

    private val viewModeFactory: ViewModelProvider.Factory by instance()
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModeFactory).get(GameViewModel::class.java)
    }

    override val kodein: Kodein by closestKodein()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardImageViewContainer.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {
                v.y = event.rawY - v.height / 2
                v.x = event.rawX - v.width / 2
            }
            true
        }

        viewModel.getGameState().observe(viewLifecycleOwner, Observer { state ->
            when (state) {
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