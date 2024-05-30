package eu.tutorials.mybookapp.Model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Category (
    val id: String,
    val category: String,
    val name: String,
): Parcelable

data class CategoriesResponse(val categories: List<Category>)