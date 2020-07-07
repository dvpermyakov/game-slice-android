package com.dvpermyakov.slice.screens.game.ui

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dvpermyakov.slice.R
import com.dvpermyakov.slice.screens.game.presentation.GameState
import com.dvpermyakov.slice.screens.game.presentation.GameViewModel
import com.dvpermyakov.slice.router.MainRouter
import kotlinx.android.synthetic.main.fragment_game.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        secondCardContainer.setTouchListener()
        viewModel.getGameState().observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is GameState.NextCard -> {
                    descriptionView.text = state.title
                    leftPicker.text = state.leftTitle
                    rightPicker.text = state.rightTitle

                    val currentCard = state.currentCard
                    secondCardContainer.visibility = View.VISIBLE
                    secondCardContainer.alpha = 1f
                    secondCardContainer.x = firstCardContainer.x
                    secondCardContainer.y = firstCardContainer.y
                    secondCardImageView.setImage(currentCard.image)
                    secondCardTitleView.text = currentCard.name

                    val nextCard = state.nextCard
                    if (nextCard != null) {
                        firstCardImageView.setImage(nextCard.image)
                        firstCardTitleView.text = nextCard.name
                        firstCardProgressBar.visibility = View.GONE
                    } else {
                        firstCardImageView.setImageResource(R.color.colorBlack)
                        firstCardTitleView.text = ""
                        firstCardProgressBar.visibility = View.VISIBLE
                    }
                }
                is GameState.GameEnd -> {
                    (activity as MainRouter).showResult(state.resultId)
                    viewModel.clear()
                }
            }
        })
    }

    private fun ImageView.setImage(image: String) {
        val stream = context.assets.open(image)
        val bitmap = BitmapFactory.decodeStream(stream)
        setImageBitmap(bitmap)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun View.setTouchListener() {
        var viewX = 0f
        var viewY = 0f
        var downX = 0f
        var downY = 0f
        setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    viewX = firstCardContainer.x
                    viewY = firstCardContainer.y
                    downX = event.rawX
                    downY = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    val diffX = max(-v.width / 2f, min(v.width / 2f, downX - event.rawX))
                    val diffY = max(-v.height / 4f, min(v.height / 4f, downY - event.rawY))
                    v.x = viewX - diffX
                    v.y = viewY - diffY
                    v.rotation = max(-30f, min(30f, -diffX * 0.1f))
                    val scale = max(-.2f, min(.2f, (diffX / v.width)))
                    leftPicker.scaleX = 1f + scale
                    leftPicker.scaleY = 1f + scale
                    rightPicker.scaleX = 1f - scale
                    rightPicker.scaleY = 1f - scale
                }
                MotionEvent.ACTION_UP -> {
                    val diffX = event.rawX - downX
                    val diffY = event.rawY - downY
                    val speed = v.width * .4f

                    val scaleX = diffX / v.width
                    if (abs(scaleX) > .1f) {
                        v.animate()
                            .setDuration(200)
                            .alpha(0f)
                            .xBy(sign(diffX) * speed)
                            .yBy(diffY / abs(diffX) * speed)
                            .withEndAction {
                                v.visibility = View.GONE
                                v.rotation = 0f
                                v.x = viewX
                                v.y = viewY
                                viewModel.chooseDeck(diffX < 0f)
                            }
                    } else {
                        v.animate().rotation(0f).x(viewX).y(viewY)
                    }
                    leftPicker.animate()
                        .setDuration(200)
                        .scaleX(1f)
                        .scaleY(1f)
                    rightPicker.animate()
                        .setDuration(200)
                        .scaleX(1f)
                        .scaleY(1f)
                }
            }
            true
        }
    }

    companion object {
        fun newInstance() = GameFragment()
    }
}