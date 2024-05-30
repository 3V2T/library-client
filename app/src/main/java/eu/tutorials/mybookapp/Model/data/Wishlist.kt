package eu.tutorials.mybookapp.Model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Wishlist (
    val id: String,
    val user_id: String,
    val book_id: String,
): Parcelable

data class WishlistResponse(val wishList: List<Wishlist>)