package com.azielu.lorempicsumbrowser.model

import android.graphics.Color

data class ImageData(val id: Int, val color: Int, val author: String)


val TempGlobalImages: List<ImageData> =
    listOf(
        ImageData(5, Color.BLUE, "aaa"),
        ImageData(6, Color.RED, "bbb"),
        ImageData(7, Color.BLACK, "ccc"),
        ImageData(8, Color.BLUE, "aaa"),
        ImageData(9, Color.RED, "bbb"),
        ImageData(10, Color.BLACK, "ccc"),
        ImageData(11, Color.BLUE, "aaa"),
        ImageData(12, Color.RED, "bbb"),
        ImageData(13, Color.BLACK, "ccc"),
    )
