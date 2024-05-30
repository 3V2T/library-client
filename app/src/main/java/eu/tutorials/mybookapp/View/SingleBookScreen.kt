package eu.tutorials.mybookapp.View

import eu.tutorials.mybookapp.SharedPreferences.SharedPreferencesManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.FabPosition
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import eu.tutorials.mybookapp.ViewModel.BookViewModel
import eu.tutorials.mybookapp.ViewModel.HistoryViewModel
import eu.tutorials.mybookapp.ViewModel.WishlistViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.pointer.pointerInput
import eu.tutorials.mybookapp.R
import eu.tutorials.mybookapp.Model.data.Server
//import com.rizzi.bouquet.ResourceType
//import com.rizzi.bouquet.rememberVerticalPdfReaderState
import eu.tutorials.mybookapp.Model.data.BookWithAuthor
import eu.tutorials.mybookapp.Model.data.service
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SingleBookScreen(
    sharedPreferences: SharedPreferencesManager,
    wishlistViewModel: WishlistViewModel,
    book: BookWithAuthor,
    bookViewModel: BookViewModel,
    navController: NavController,
    openWebViewPdf: (path: String, title: String) -> Unit,
    historyViewModel: HistoryViewModel
    // navigateToPDFPage: (String) -> Unit
) {
    var isBehaviorBtnVisible by remember { mutableStateOf(false) }
    var visibleBtn by remember { mutableStateOf(false) }
    val thumbnailPath = "http://${Server.DEFAULT_PORT}${book.cover_path}"
    val pdfFilePath = "http://${Server.DEFAULT_PORT}${book.file_path}"
    var isExisted by remember { mutableStateOf(false) }
    var lastInteractionTime by remember { mutableStateOf(System.currentTimeMillis()) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        val wishlistDeferred = fetchWishlist(sharedPreferences.getToken()!!)
        val wishlist = wishlistDeferred.await()
        isExisted = wishlist.contains(book)
    }
    LaunchedEffect(lastInteractionTime) {
        scope.launch {
            while (true) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastInteractionTime > 5000) {
                    isBehaviorBtnVisible = false
                    visibleBtn = false
                }
                delay(1000) // Kiểm tra mỗi giây
            }
        }
    }
    Scaffold(
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures {
                lastInteractionTime = System.currentTimeMillis()
                isBehaviorBtnVisible = !isBehaviorBtnVisible
                visibleBtn = !visibleBtn
            }
        },
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = book.title,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.text_bar_color)
                    )
                },
                backgroundColor = colorResource(id = R.color.app_bar_color)
            )
        },
        floatingActionButton = {
            Column {
                ExtendedFloatingActionButton(
                    text = {
                        if(isExisted) Text(text = "Eject") else  Text(text = "Save")
                           },
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .alpha(
                            if (visibleBtn && sharedPreferences.isLogin()) 1f else 0f
                        ),
                    contentColor = colorResource(id = R.color.text_bar_color),
                    backgroundColor = colorResource(id = R.color.app_bar_color),
                    icon = {Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = Color.Red)},
                    onClick = {
                        if(visibleBtn) {
                            if(isExisted) {
                                isExisted = false
                                wishlistViewModel.removeBookFromWishlist(
                                    sharedPreferences.getToken()!!,
                                    book.id
                                )
                            }
                            else {
                                isExisted = true
                                wishlistViewModel.addBookToWishlist(
                                    sharedPreferences.getToken()!!,
                                    book.id
                                )
                            }
                        } },
                )
                ExtendedFloatingActionButton(
                    text = { Text(text = "Read") },
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .alpha(
                            if (visibleBtn) 1f else 0f
                        ),
                    contentColor = colorResource(id = R.color.text_bar_color),
                    backgroundColor = colorResource(id = R.color.app_bar_color),
                    icon = {Icon(imageVector = Icons.Default.AutoStories , contentDescription = null)},
                    onClick = {
                        if(visibleBtn) {
                            historyViewModel.addEventToHistory(sharedPreferences.getToken()!!, book.id)
                            openWebViewPdf(pdfFilePath, book.title)
                        }
                    },
                )
                ExtendedFloatingActionButton(
                    text = { Text(text = "Exit") },
                    modifier = Modifier
                        .alpha(
                            if (visibleBtn) 1f else 0f
                        ),
                    contentColor = Color.Black,
                    backgroundColor = Color.White,
                    icon = {Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)},
                    onClick = {
                        if(visibleBtn) {
                            navController.navigateUp()
                        } },
                )
                FloatingActionButton(
                    modifier = Modifier.padding(all = 20.dp).alpha(
                        if (isBehaviorBtnVisible) 1f else 0f
                    ),
                    contentColor = colorResource(id = R.color.text_bar_color),
                    backgroundColor = colorResource(id = R.color.app_bar_color),
                    onClick = {
                        if(isBehaviorBtnVisible) {
                            visibleBtn = !visibleBtn
                        }
                              },
                ) {
                    Icon(
                        imageVector =
                        if(!visibleBtn) Icons.Default.Add
                        else Icons.Default.Close,
                        contentDescription = null)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.text_bar_color))
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))
            Image(
                painter = rememberAsyncImagePainter(thumbnailPath),
                contentDescription = "${book.title} Thumbnail",
                modifier = Modifier
                    .wrapContentSize()
                    .aspectRatio(1f)

            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = book.description,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            )
        }
    }
}

suspend fun fetchWishlist(token: String): Deferred<MutableList<BookWithAuthor>> {
    return coroutineScope {
        async {
            try {
                val bookList: MutableList<BookWithAuthor> = mutableListOf()
                val response = service.getWishListByToken("Bearer $token")
                for (wish in response.wishList) {
                    val response2 = service.getBookById(wish.book_id)
                    val response3 = service.getAuthor(response2.books[0].author_id)
                    val bookWithAuthor = BookWithAuthor(
                        id = response2.books[0].id,
                        title = response2.books[0].title,
                        author_id = response2.books[0].author_id,
                        category_id = response2.books[0].category_id,
                        cover_path = response2.books[0].cover_path,
                        file_path = response2.books[0].file_path,
                        description = response2.books[0].description,
                        author = response3.author[0].author,
                        published = response2.books[0].published
                    )
                    bookList.add(bookWithAuthor)
                }
                bookList
            } catch (e: Exception) {
                println(e)
                mutableListOf<BookWithAuthor>() // Trả về danh sách trống hoặc giá trị mặc định khác tùy thuộc vào yêu cầu của bạn
            }
        }
    }
}