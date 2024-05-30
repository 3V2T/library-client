package eu.tutorials.mybookapp.Model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserCredential (
    val id: String,
    val username: String,
    val accessToken: String,
): Parcelable

data class CredentialResponse(val user: UserCredential)