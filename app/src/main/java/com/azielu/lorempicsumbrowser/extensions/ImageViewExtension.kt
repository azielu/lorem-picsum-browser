package com.azielu.lorempicsumbrowser.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.azielu.lorempicsumbrowser.R
import com.azielu.lorempicsumbrowser.model.ImageData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition

@SuppressLint("CheckResult")
fun ImageView.setUrlImage(context: Context, image: ImageData, onImageLoadingError: (() -> Unit)? = null) {
    if (TextUtils.isEmpty(image.url)) {
        setImageResource(R.drawable.ic_loading_error)
    } else {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 100f
        circularProgressDrawable.start()

        Glide.with(context)
            .load(image.url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(
                RequestOptions()
                    .placeholder(circularProgressDrawable)
                    .error(R.drawable.ic_loading_error)
                    .fallback(R.drawable.ic_loading_error)
            )
            .fitCenter()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    onImageLoadingError?.let { it() }
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            }).into(this)
    }
}
