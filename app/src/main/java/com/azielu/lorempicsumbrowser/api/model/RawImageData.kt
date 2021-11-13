package com.azielu.lorempicsumbrowser.api.model

import com.google.gson.annotations.SerializedName

data class RawImageData(
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerializedName("download_url") val downloadUrl: String
) {
}
