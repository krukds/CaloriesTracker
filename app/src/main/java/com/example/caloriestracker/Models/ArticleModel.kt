package com.example.caloriestracker.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleModel(
    val title: String,
    val text: String,
    val links: List<Link>
): Parcelable

@Parcelize
data class Link(
    val title: String,
    val link: String,
    val id: Int
): Parcelable
