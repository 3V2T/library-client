package eu.tutorials.mybookapp.View

import eu.tutorials.mybookapp.SharedPreferences.SharedPreferencesManager
import eu.tutorials.mybookapp.ViewModel.BookViewModel
import eu.tutorials.mybookapp.ViewModel.HistoryViewModel
import eu.tutorials.mybookapp.ViewModel.UserViewModel
import eu.tutorials.mybookapp.ViewModel.WishlistViewModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import eu.tutorials.mybookapp.Component.BottomAppBarView
import eu.tutorials.mybookapp.R
import eu.tutorials.mybookapp.Navigator.Screen

@Composable
fun UserScreen(
    wishlistViewModel: WishlistViewModel,
    bookViewModel: BookViewModel,
    navController: NavController,
    userViewModel: UserViewModel,
    sharedPreferences: SharedPreferencesManager,
    historyViewModel: HistoryViewModel
) {
    var iExpended by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(80.dp),
                title = {
                    Spacer(Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo")
                        Column {
                            TextButton(
                                onClick = {
                                    iExpended = true
                                },
                                modifier = Modifier.alpha(
                                    if (sharedPreferences.isLogin()) 1f else 0f
                                ),
                            ) {
                                Icon(
                                    modifier = Modifier.size(48.dp),
                                    imageVector = Icons.Rounded.ArrowDropDown,
                                    contentDescription = "dropdown icon",
                                    tint = colorResource(id = R.color.text_bar_color)
                                )
                            }
                            DropdownMenu(expanded = iExpended, onDismissRequest = { iExpended = false }) {
                                DropdownMenuItem(
                                    text = { androidx.compose.material3.Text(text = "History") },
                                    onClick = {
                                        iExpended = false
                                        navController.navigate(Screen.HistoryScreen.route)
                                    }
                                )
                                DropdownMenuItem(
                                    text = { androidx.compose.material3.Text(text = "Wishlist") },
                                    onClick = {
                                        iExpended = false
                                        navController.navigate(Screen.WishlistScreen.route)
                                    }
                                )
                                DropdownMenuItem(
                                    text = { androidx.compose.material3.Text(text = "Logout") },
                                    onClick = {
                                        iExpended = false
                                        userViewModel.clearUserSession()
                                        sharedPreferences.clearSession()
                                        wishlistViewModel.clearWishlist()
                                        historyViewModel.clearHistory()
                                        navController.navigate(Screen.UserScreen.route)
                                    }
                                )
                            }

                        }
                    }
                    Spacer(Modifier.height(10.dp))
                },
                elevation = 3.dp,
                backgroundColor = colorResource(id = R.color.app_bar_color),
            )
         },
        bottomBar = { BottomAppBarView(navController = navController, bookViewModel) },
        floatingActionButton = {
            if(sharedPreferences.isLogin()) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.WishlistScreen.route) },
                    contentColor = colorResource(id = R.color.text_bar_color),
                    backgroundColor = colorResource(id = R.color.app_bar_color),
                    modifier = Modifier.padding(all = 20.dp),
                    shape = CircleShape
                ) {
                    Icon(Icons.Filled.Favorite, "wishlist button", tint = Color.Red)
                }
            }
        }
    ) {
        userViewModel.fetchCurrentUser(sharedPreferences.getToken()!!)
        if(sharedPreferences.isLogin() && userViewModel.userState.value.user.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(colorResource(id = R.color.text_bar_color)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val name: String? = userViewModel.userState.value?.user?.get(0)?.name
                val email: String? = userViewModel.userState.value?.user?.get(0)?.email
                val username: String? = userViewModel.userState.value?.user?.get(0)?.username
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.author),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .border(
                            BorderStroke(8.dp, colorResource(id = R.color.app_bar_color)),
                            CircleShape
                        )
                )
                Spacer(Modifier.height(50.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        Text(text = "Name: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = "${name}", fontSize = 20.sp)
                    }
//                    Spacer(Modifier.height(10.dp))
//                    Row {
//                        Text(text = "Username: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
//                        Text(text = "${username}", fontSize = 20.sp)
//                    }
                    Spacer(Modifier.height(10.dp))
                    Row {
                        Text(text = "Email: ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = "${email}", fontSize = 20.sp)
                    }
                }
                Spacer(Modifier.height(50.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { navController.navigate(Screen.ChangeInfoScreen.route) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.app_bar_color)
                        )
                    ) {
                        Text(
                            text = "Change Info",
                            color = colorResource(id = R.color.text_bar_color)
                        )
                    }
                    Spacer(Modifier.width(20.dp))
                    Button(
                        onClick = { navController.navigate(Screen.ChangePassScreen.route) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.app_bar_color)
                        )
                    ) {
                        Text(
                            text = "Change Password",
                            color = colorResource(id = R.color.text_bar_color)
                        )
                    }
//                    Spacer(Modifier.width(10.dp))
//                    OutlinedButton(
//                        onClick = {
//                            userViewModel.clearUserSession()
//                            sharedPreferences.clearSession()
//                            wishlistViewModel.clearWishlist()
//                            historyViewModel.clearHistory()
//                            navController.navigate(Screen.UserScreen.route)
//                                  },
//                        colors = ButtonDefaults.buttonColors(
//                            backgroundColor = colorResource(id = R.color.white)
//                        )
//                    ) {
//                        Text(text = "Logout")
//                    }
                }
            }
        }
        else {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(colorResource(id = R.color.text_bar_color)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Not a member yet?",
                    textAlign = TextAlign.Center,
                    color= colorResource(id = R.color.app_bar_color),
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { navController.navigate(Screen.LoginScreen.route) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.app_bar_color)
                        )
                    ) {
                        Text(
                            text = "Sign in",
                            color = colorResource(id = R.color.text_bar_color)
                        )
                    }
                    Spacer(Modifier.width(30.dp))
                    OutlinedButton(
                        onClick = { navController.navigate(Screen.RegisterScreen.route) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.white)
                        )
                    ) {
                        Text(text = "Sign up")
                    }
                }
            }
        }
    }
}