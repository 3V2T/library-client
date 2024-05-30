package eu.tutorials.mybookapp.Model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val id: String,
    val username: String,
    val password: String,
    val name: String,
    val email: String
): Parcelable

data class UserResponse(val user: List<User>)