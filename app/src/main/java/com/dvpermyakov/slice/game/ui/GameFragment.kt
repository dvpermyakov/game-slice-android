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
import com.dvpermyakov.slice.router.MainRouter
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

        dynamicCardImageViewContainer.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    v.y = event.rawY - v.height / 2
                    v.x = event.rawX - v.width / 2
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_OUTSIDE -> {
                    viewModel.nextCard()
                    v.x = staticCardImageViewContainer.x
                    v.y = staticCardImageViewContainer.y
                }
            }
            true
        }

        viewModel.getGameState().observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is GameState.NextCard -> {
                    descriptionView.text = state.title
                    leftPicker.text = state.leftTitle
                    rightPicker.text = state.rightTitle

                    val currentCard = state.currentCard
                    dynamicCardImageView.post {
                        if (dynamicCardImageView != null) {
                            Picasso.get()
                                .load(currentCard.image)
                                .resize(
                                    dynamicCardImageView.measuredWidth,
                                    dynamicCardImageView.measuredHeight
                                )
                                .centerCrop()
                                .transform(RoundedCornersTransformation(32, 0))
                                .into(dynamicCardImageView)
                        }
                    }
                    dynamicCardTitleView.text = currentCard.name

                    val nextCard = state.nextCard
                    if (nextCard != null) {
                        staticCardImageView.post {
                            if (staticCardImageView != null) {
                                Picasso.get()
                                    .load(nextCard.image)
                                    .resize(
                                        staticCardImageView.measuredWidth,
                                        staticCardImageView.measuredHeight
                                    )
                                    .centerCrop()
                                    .transform(RoundedCornersTransformation(32, 0))
                                    .into(staticCardImageView)
                            }
                        }
                        staticCardTitleView.text = nextCard.name
                    }
                }
                is GameState.GameEnd -> {
                    (activity as MainRouter).showResult()
                    viewModel.clear()
                }
            }
        })
    }

    companion object {
        fun newInstance() = GameFragment()
    }
}