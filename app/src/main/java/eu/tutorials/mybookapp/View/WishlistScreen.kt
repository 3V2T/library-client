package eu.tutorials.mybookapp.View

import eu.tutorials.mybookapp.SharedPreferences.SharedPreferencesManager
import eu.tutorials.mybookapp.ViewModel.AuthorViewModel
import eu.tutorials.mybookapp.ViewModel.BookViewModel
import eu.tutorials.mybookapp.ViewModel.WishlistViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import eu.tutorials.mybookapp.Component.BottomAppBarView
import eu.tutorials.mybookapp.R
import eu.tutorials.mybookapp.Model.data.Server
import eu.tutorials.mybookapp.Model.data.BookWithAuthor

@Composable
fun WishlistScreen(
    modifier: Modifier = Modifier,
    bookViewModel: BookViewModel,
    viewState: WishlistViewModel.WishlistState,
    navController: NavController,
    navigateToDetail: (BookWithAuthor) -> Unit,
    sharedPreferences: SharedPreferencesManager
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Wishlist",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.text_bar_color)
                    )
                },
                backgroundColor = colorResource(id = R.color.app_bar_color)
            )
        },
        bottomBar = { BottomAppBarView(navController = navController, bookViewModel=bookViewModel) }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.text_bar_color))) {
            when {
                viewState.loading -> {
                    CircularProgressIndicator(modifier.align(Alignment.Center))
                }
                viewState.error != null -> {
                    NotFoundWishlistScreen()
                }
                else -> {
                    // Display Books
                    WishlistScreen(wishlist = viewState.wishlist, padding = it, navigateToDetail = navigateToDetail)
                }
            }
        }
    }
}
@Composable
fun WishlistScreen(
    wishlist: List<BookWithAuthor>,
    padding: PaddingValues,
    navigateToDetail: (BookWithAuthor) -> Unit
) {
    LazyVerticalGrid(GridCells.Fixed(2), modifier = Modifier
        .fillMaxSize()
        .padding(padding)) {
        items(wishlist) {
            book ->
            WishlistItem(book = book, navigateToDetail = navigateToDetail)
        }
    }
}

@Composable
fun WishlistItem(
    book: BookWithAuthor,
    navigateToDetail: (BookWithAuthor) -> Unit,
    authorViewModel : AuthorViewModel = AuthorViewModel()
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable {
                navigateToDetail(book)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        val thumbnailPath = "http://${Server.DEFAULT_PORT}${book.cover_path}"
        Spacer(Modifier.height(10.dp))
        Image (
            painter = rememberAsyncImagePainter(thumbnailPath),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = book.author,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp),
            textAlign = TextAlign.Center
        )
        Text (
            text = book.title,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(10.dp))
    }
}
