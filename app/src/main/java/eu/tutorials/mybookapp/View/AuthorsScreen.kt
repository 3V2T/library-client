package eu.tutorials.mybookapp.View


import eu.tutorials.mybookapp.SharedPreferences.SharedPreferencesManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import eu.tutorials.mybookapp.ViewModel.AuthorViewModel
import eu.tutorials.mybookapp.ViewModel.BookViewModel
import eu.tutorials.mybookapp.Component.BottomAppBarView
import eu.tutorials.mybookapp.R
import eu.tutorials.mybookapp.Component.TopAppBarView
import eu.tutorials.mybookapp.Model.data.Author

@Composable
fun AuthorsScreen(
    authorViewModel: AuthorViewModel,
    bookViewModel: BookViewModel,
    viewState: AuthorViewModel.AuthorState,
    navController: NavController,
    navigateToDetail: (String) -> Unit,
    searchByKeyword: (String) -> Unit,
    sharedPreferences: SharedPreferencesManager
) {
    Scaffold(
        topBar = {
            TopAppBarView(
                searchByKeyword = searchByKeyword,
                searchValue = authorViewModel.searchValue,
                sharedPreferences=sharedPreferences,
                navController = navController)
        },
        bottomBar = { BottomAppBarView(navController = navController, bookViewModel=bookViewModel, authorViewModel=authorViewModel) }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.text_bar_color))) {
            when {
                viewState.loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                viewState.error != null -> {
                    NotFoundAuthorScreen()
                }
                else -> {
                    // Display Books
                    AuthorsScreen(authors = viewState.list, padding = it, navigateToDetail = navigateToDetail)
                }
            }
        }
    }
}

@Composable
fun AuthorsScreen(
    authors: List<Author>,
    padding: PaddingValues,
    navigateToDetail: (String) -> Unit
) {
    LazyVerticalGrid(
        GridCells.Fixed(4), modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
        items(authors) {
                author ->
            AuthorItem(author = author, navigateToDetail = navigateToDetail)
        }
    }
}

@Composable
fun AuthorItem(
    author: Author,
    navigateToDetail: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable {
                navigateToDetail(author.description)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Spacer(Modifier.height(10.dp))
        Image (
            painter = rememberAsyncImagePainter(R.drawable.author),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        )
        Spacer(Modifier.height(10.dp))
        Text (
            text = author.author,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(10.dp))
    }
}