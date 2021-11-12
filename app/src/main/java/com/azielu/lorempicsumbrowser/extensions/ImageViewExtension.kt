package com.azielu.lorempicsumbrowser.extensions

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.azielu.lorempicsumbrowser.model.ImageData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.setUrlImage(context: Context, image: ImageData) {
        if (TextUtils.isEmpty(image.url)) {
            setImageResource(android.R.drawable.stat_notify_error)
        } else {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 100f
            circularProgressDrawable.start()

            Glide.with(context)
                .load(image.url)
                .apply(
                    RequestOptions()
                        .placeholder(circularProgressDrawable)
                        .error(android.R.drawable.stat_notify_error)
                        .fallback(android.R.drawable.stat_notify_error)
                )
                .fitCenter()
                .into(this)
        }
    }
