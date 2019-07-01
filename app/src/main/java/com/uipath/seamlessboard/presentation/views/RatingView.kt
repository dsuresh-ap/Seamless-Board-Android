package com.uipath.seamlessboard.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.*
import androidx.lifecycle.MutableLiveData
import com.uipath.seamlessboard.R
import kotlinx.android.synthetic.main.view_rating.view.*

@InverseBindingMethods(
    InverseBindingMethod(attribute = "rating", type = RatingView::class)
)
class RatingView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    companion object {
        @BindingAdapter("rating")
        @JvmStatic
        fun setRating(ratingView: RatingView, valueLiveData: MutableLiveData<Float>) {
            valueLiveData.value?.let {
                if (ratingView.rating != it) {
                    ratingView.rating = it
                }
            }
        }

        @InverseBindingAdapter(attribute = "rating")
        @JvmStatic
        fun getRating(ratingView: RatingView): Float {
            return ratingView.rating
        }

        @BindingAdapter("app:ratingAttrChanged")
        @JvmStatic
        fun setListeners(ratingView: RatingView, attrChange: InverseBindingListener) {
            ratingView.ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
                if (fromUser) {
                    attrChange.onChange()
                }
            }
        }
    }

    var name: String
        get() = nameTextView.text.toString()
        set(value) {
            nameTextView.text = value
        }

    var rating: Float
        get() = ratingBar.rating
        set(value) {
            ratingBar.rating = value
        }

    init {
        View.inflate(context, R.layout.view_rating, this)
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.RatingView)
            nameTextView.text = a.getString(R.styleable.RatingView_text)
            ratingBar.rating = a.getFloat(R.styleable.RatingView_rating, 0f)
            ratingBar.setIsIndicator(a.getBoolean(R.styleable.RatingView_isIndicator, false))
            a.recycle()
        }
    }
}