package eu.tutorials.mybookapp.Model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Author (
    val id: String,
    val author: String,
    val description: String,
): Parcelable

data class AuthorsResponse(val author: List<Author>)
data class AuthorResponse(val author: List<Author>)