package eu.tutorials.mybookapp.Model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Book (
    val id: String,
    val title: String,
    val author_id: String,
    val category_id: String,
    val cover_path: String,
    val file_path: String,
    val description: String,
    val published: Date
): Parcelable
@Parcelize
data class BookWithAuthor (
    val id: String,
    val title: String,
    val author_id: String,
    val category_id: String,
    val cover_path: String,
    val file_path: String,
    val description: String,
    val author: String,
    val published: Date
): Parcelable

data class BooksResponse(val books: List<Book>)