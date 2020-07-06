package com.dvpermyakov.slice.game.ui

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
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
import kotlinx.android.synthetic.main.fragment_game.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

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

        var downX = 0f
        var downY = 0f
        dynamicCardImageViewContainer.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.rawX
                    downY = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    val diffX = max(-v.width / 2f, min(v.width / 2f, downX - event.rawX))
                    val diffY = max(-v.height / 4f, min(v.height / 4f, downY - event.rawY))
                    v.x = staticCardImageViewContainer.x - diffX
                    v.y = staticCardImageViewContainer.y - diffY
                    v.rotation = max(-30f, min(30f, -diffX * 0.1f))
                    val scale = max(-.2f, min(.2f, (diffX / v.width)))
                    leftPicker.scaleX = 1f + scale
                    leftPicker.scaleY = 1f + scale
                    rightPicker.scaleX = 1f - scale
                    rightPicker.scaleY = 1f - scale
                }
                MotionEvent.ACTION_UP -> {
                    val diffX = downX - event.rawX
                    if (abs(diffX) > v.width / 10f) {
                        viewModel.chooseDeck(diffX > 0f)
                    }
                    v.x = staticCardImageViewContainer.x
                    v.y = staticCardImageViewContainer.y
                    v.rotation = 0f
                    leftPicker.scaleX = 1f
                    leftPicker.scaleY = 1f
                    rightPicker.scaleX = 1f
                    rightPicker.scaleY = 1f
                }
            }
            true
        }

        viewModel.getGameState().observe(viewLifecycleOwner, Observer { state ->
            val assetManager = requireContext().assets

            when (state) {
                is GameState.NextCard -> {
                    descriptionView.text = state.title
                    leftPicker.text = state.leftTitle
                    rightPicker.text = state.rightTitle

                    val currentCard = state.currentCard
                    val currentCardBitmap =
                        BitmapFactory.decodeStream(assetManager.open(currentCard.image))
                    dynamicCardImageView.setImageBitmap(currentCardBitmap)
                    dynamicCardTitleView.text = currentCard.name

                    val nextCard = state.nextCard
                    if (nextCard != null) {
                        val nextCardBitmap =
                            BitmapFactory.decodeStream(assetManager.open(nextCard.image))
                        staticCardImageView.setImageBitmap(nextCardBitmap)
                        staticCardTitleView.text = nextCard.name
                    }
                }
                is GameState.GameEnd -> {
                    (activity as MainRouter).showResult(state.resultId)
                    viewModel.clear()
                }
            }
        })
    }

    companion object {
        fun newInstance() = GameFragment()
    }
}