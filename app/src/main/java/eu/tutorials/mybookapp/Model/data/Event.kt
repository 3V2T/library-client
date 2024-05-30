package eu.tutorials.mybookapp.Model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event (
    val id: String,
    val user_id: String,
    val book_id: String,
    val last_read: String
): Parcelable

@Parcelize
data class EventInfo (
    val id: String,
    val nameOfBook : String,
    val author: String,
    val book_id: String,
    val last_read: String,
    val file_path: String,
): Parcelable

data class HistoryResponse(val history: List<Event>)