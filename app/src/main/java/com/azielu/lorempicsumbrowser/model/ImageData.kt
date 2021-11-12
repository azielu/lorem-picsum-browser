package com.azielu.lorempicsumbrowser.model

data class ImageData(val id: Int, val url: String, val author: String)


val TempGlobalImages: List<ImageData> =
    mutableListOf<ImageData>().apply {
        for (i in 1..100) {
            this.add(ImageData(i, "https://picsum.photos/200?image=$i", "aaa $i"))
        }
    }
