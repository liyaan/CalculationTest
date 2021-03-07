package com.liyaan.calculationtest.gallery.bean

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Paxabay(
    val hits: MutableList<PhotoItem>,
    val total: Int,
    val totalHits: Int
)

@Parcelize data class PhotoItem(
        val id: Int,
        @SerializedName("webformatURL")val fullUrl: String,
        @SerializedName("previewURL")val previewUrl: String
):Parcelable