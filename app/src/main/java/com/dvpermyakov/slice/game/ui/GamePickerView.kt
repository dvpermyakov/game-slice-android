package com.dvpermyakov.slice.game.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.dvpermyakov.slice.R
import kotlinx.android.synthetic.main.view_game_picker.view.*

class GamePickerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val title: String
    private val image: Drawable?

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.GamePickerView, 0, 0).apply {
            try {
                title = getString(R.styleable.GamePickerView_title).orEmpty()
                image = getDrawable(R.styleable.GamePickerView_image)
            } finally {
                recycle()
            }
        }
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_game_picker, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (image != null) {
            gamePickerImage.setImageDrawable(image)
        }
        gamePickerText.text = title
    }
}