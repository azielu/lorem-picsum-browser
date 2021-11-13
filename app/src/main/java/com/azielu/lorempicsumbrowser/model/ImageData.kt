package com.azielu.lorempicsumbrowser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageData(
    val id: Int,
    val author: String,
) : Parcelable {
    val url: String get() = "https://picsum.photos/200?image=$id"
}
