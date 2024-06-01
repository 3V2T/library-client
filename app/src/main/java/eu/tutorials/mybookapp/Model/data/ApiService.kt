package eu.tutorials.mybookapp.Model.data

import eu.tutorials.mybookapp.View.CredentialPass
import eu.tutorials.mybookapp.View.Credentials
import eu.tutorials.mybookapp.View.Info
import eu.tutorials.mybookapp.View.RegisterInfo
import eu.tutorials.mybookapp.ViewModel.HistoryViewModel
import eu.tutorials.mybookapp.ViewModel.WishlistViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class Server(
    val port: String
) {
    companion object {
        const val DEFAULT_PORT = "lapi.ayclqt.id.vn"
    }
}

private val retrofit = Retrofit.Builder().baseUrl("https://${Server.DEFAULT_PORT}/api/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

// liên kết baseUrl với final point
val service = retrofit.create(ApiService::class.java)

interface ApiService {
    @GET("book")
    suspend fun getBooks(
        @Query("category") category: String,
        @Query("author") author: String,
    ): BooksResponse

    @GET("book/search")
    suspend fun getBooksByKeyword(
        @Query("keyword") keyword: String
    ): BooksResponse

    @GET("book/{id}")
    suspend fun getBookById(@Path("id") id:String): BooksResponse

    @GET("category")
    suspend fun getCategories(): CategoriesResponse

    @GET("author")
    suspend fun getAuthors(): AuthorsResponse
    @GET("author/{id}")
    suspend fun getAuthor(@Path("id") id:String): AuthorResponse
    @GET("author/search")
    suspend fun getAuthorsByKeyword(
        @Query("name") name: String
    ): AuthorResponse


    @POST("user/login")
    suspend fun login(@Body credentials: Credentials): CredentialResponse
    @POST("user/register")
    suspend fun register(@Body registerInfo: RegisterInfo)
    @POST("user/changeInfo")
    suspend fun changeInfo(@Body info: Info, @Header("Authorization") authHeader: String)
    @POST("user/changePassword")
    suspend fun changePass(@Body credentialPass: CredentialPass, @Header("Authorization") authHeader: String)

    @GET("user/getUser")
    suspend fun getCurrentUser(@Header("Authorization") authHeader: String): UserResponse
    @POST("wishlist")
    suspend fun addBookToUserWishlist(@Body bookId: WishlistViewModel.BookId, @Header("Authorization") authHeader: String)
    @DELETE("wishlist/{id}")
    suspend fun removeBookFromUserWishlist(@Path("id") bookId: String, @Header("Authorization") authHeader: String)
    @GET("wishlist")
    suspend fun getWishListByToken(@Header("Authorization") authHeader: String): WishlistResponse
    @GET("history")
    suspend fun getHistoryByToken(@Header("Authorization") authHeader: String): HistoryResponse
    @POST("history")
    suspend fun addEventToHistory(@Body eventBody: HistoryViewModel.EventBody, @Header("Authorization") authHeader: String)
}