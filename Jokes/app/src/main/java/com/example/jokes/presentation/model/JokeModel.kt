package com.example.jokes.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JokeModel (
    val id: Int? = null,
    val type: String? = null,
    val setup: String? = null,
    val punchline: String? = null,
    var closed: Boolean = true
): Parcelable
