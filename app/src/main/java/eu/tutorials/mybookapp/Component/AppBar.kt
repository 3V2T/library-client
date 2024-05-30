package eu.tutorials.mybookapp.Component

import eu.tutorials.mybookapp.SharedPreferences.SharedPreferencesManager
import eu.tutorials.mybookapp.ViewModel.AuthorViewModel
import eu.tutorials.mybookapp.ViewModel.BookViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomAppBar
import androidx.compose.material.TextButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import eu.tutorials.mybookapp.Navigator.Screen
import eu.tutorials.mybookapp.R
import eu.tutorials.mybookapp.View.AuthorsScreen
import eu.tutorials.mybookapp.View.CategoriesScreen
import eu.tutorials.mybookapp.View.HomeScreen
import eu.tutorials.mybookapp.View.UserScreen
import eu.tutorials.mybookapp.View.WishlistScreen

//Home
@Composable
fun TopAppBarView(
    searchByKeyword: (String) -> Unit,
    searchValue: String,
    sharedPreferences : SharedPreferencesManager,
    navController: NavController
) {
    TopAppBar(
        modifier = Modifier.height(140.dp),
        title = {
            Column (
            ) {
                Spacer(Modifier.height(3.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo")
                   TextButton(
                       onClick = { navController.navigate(Screen.WishlistScreen.route)  },
                       modifier = Modifier.alpha(
                           if (sharedPreferences.isLogin()) 1f else 0f
                       ),
                   ) {
                        Icon(
                            imageVector = Icons.Rounded.Favorite,
                            contentDescription = "wishlist icon",
                            tint = colorResource(id = R.color.text_bar_color)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                SearchTextField(value = searchValue, searchByKeyword = searchByKeyword)
                Spacer(modifier = Modifier.height(5.dp))
            }
        },
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.app_bar_color),
    )
}
@Composable
fun TopAppBarViewWithoutField() {
    TopAppBar(
        modifier = Modifier.height(80.dp),
        title = {
            Spacer(Modifier.height(3.dp))
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo")
            Spacer(Modifier.height(15.dp))
        },
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.app_bar_color),
    )
}

//Home
@Composable
fun BottomAppBarView(
    navController: NavController,
    bookViewModel: BookViewModel = viewModel(),
    authorViewModel: AuthorViewModel = viewModel()
) {
    BottomAppBar(
        modifier = Modifier.height(64.dp),
        backgroundColor = colorResource(id = R.color.app_bar_color)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Home
            TextButton(
                onClick = {
                    bookViewModel.fetchBooks()
                    navController.navigate(Screen.HomeScreen.route) },
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Home,
                        contentDescription = "",
                        tint = colorResource(id = R.color.text_bar_color)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Home", color = colorResource(id = R.color.text_bar_color))
                }
            }
            // Category
            TextButton(onClick = { navController.navigate(Screen.CategoriesScreen.route) }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Create,
                        contentDescription = "",
                        tint = colorResource(id = R.color.text_bar_color)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Categories", color = colorResource(id = R.color.text_bar_color))
                }
            }
            // Author
            TextButton(
                onClick = {
                    authorViewModel.fetchAuthors()
                    navController.navigate(Screen.AuthorsScreen.route)
                }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Face,
                        contentDescription = "",
                        tint = colorResource(id = R.color.text_bar_color)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Authors", color = colorResource(id = R.color.text_bar_color))
                }
            }
            // User
            TextButton(onClick = { navController.navigate(Screen.UserScreen.route) }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = "",
                        tint = colorResource(id = R.color.text_bar_color)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "User", color = colorResource(id = R.color.text_bar_color))
                }
            }
        }
    }
}


@Composable
fun SearchTextField(
    value: String,
    searchByKeyword: (String) -> Unit
) {
    BasicTextField(
        value = value,
        onValueChange = {searchByKeyword(it)},
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        decorationBox = {
                innerTextField ->
                    Row(
                        Modifier
                            .background(
                                colorResource(id = R.color.text_bar_color),
                                RoundedCornerShape(percent = 30)
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Search, contentDescription = null)
                        Spacer(Modifier.width(16.dp))
                        innerTextField()
                    }
        },
    )
}




