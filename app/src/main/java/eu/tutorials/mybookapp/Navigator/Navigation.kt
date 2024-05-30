package eu.tutorials.mybookapp.Navigator

import eu.tutorials.mybookapp.View.AuthorsScreen
import eu.tutorials.mybookapp.View.CategoriesScreen
import eu.tutorials.mybookapp.View.ChangeInfoScreen
import eu.tutorials.mybookapp.View.ChangePassScreen
import eu.tutorials.mybookapp.View.HistoryScreen
import eu.tutorials.mybookapp.View.HomeScreen
import eu.tutorials.mybookapp.View.LoginScreen
import eu.tutorials.mybookapp.View.RegisterScreen
import eu.tutorials.mybookapp.View.SingleBookScreen
import eu.tutorials.mybookapp.View.UserScreen
import eu.tutorials.mybookapp.View.WishlistScreen
import eu.tutorials.mybookapp.SharedPreferences.SharedPreferencesManager
import eu.tutorials.mybookapp.ViewModel.AuthorViewModel
import eu.tutorials.mybookapp.ViewModel.BookViewModel
import eu.tutorials.mybookapp.ViewModel.CategoryViewModel
import eu.tutorials.mybookapp.ViewModel.HistoryViewModel
import eu.tutorials.mybookapp.ViewModel.UserViewModel
import eu.tutorials.mybookapp.ViewModel.WishlistViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.tutorials.mybookapp.Model.data.BookWithAuthor
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    sharedPreferences: SharedPreferencesManager,
    openWebViewPdf: (path: String, title: String) -> Unit,
    bookViewModel: BookViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel(),
    authorViewModel: AuthorViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(),
    wishlistViewModel: WishlistViewModel = viewModel(),
    historyViewModel: HistoryViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            val bookViewState by bookViewModel.booksState
            HomeScreen(
                bookViewModel = bookViewModel,
                viewState = bookViewState,
                navController = navController,
                sharedPreferences = sharedPreferences,
                navigateToDetail = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("book", it)
                    navController.navigate(Screen.SingleBook.route)
                },
                searchByKeyword = {
                    bookViewModel.onSearchValueChange(it)
                    bookViewModel.fetchBooksByKeyword(it)
                }
            )
        }
        composable(Screen.SingleBook.route) {
            val book = navController.previousBackStackEntry?.savedStateHandle?.
            get<BookWithAuthor>("book") ?: BookWithAuthor("","","","","","","", "",Date())
            SingleBookScreen(
                bookViewModel = bookViewModel,
                book = book,
                navController = navController,
                openWebViewPdf = openWebViewPdf,
                sharedPreferences = sharedPreferences,
                wishlistViewModel = wishlistViewModel,
                historyViewModel = historyViewModel,
//                navigateToPDFPage = {
//                navController.currentBackStackEntry?.savedStateHandle?.set("pdfPath", it)
//                navController.navigate(Screen.PDFViewer.route)
//                }
            )
        }
//        composable(Screen.PDFViewer.route) {
//            val uri = navController.previousBackStackEntry?.savedStateHandle?.
//            get<String>("pdfPath") ?: ""
//            pdfViewer(uri = uri)
//        }
        composable(Screen.CategoriesScreen.route) {
            val categoryViewState by categoryViewModel.categoriesState
            CategoriesScreen(
                bookViewModel = bookViewModel,
                viewState = categoryViewState,
                navController = navController,
                navigateToDetail = {
                    bookViewModel.fetchBooksByCategory(it)
                    navController.navigate(Screen.HomeScreen.route)
                },
                searchByKeyword = {

                }
            )
        }

        composable(Screen.AuthorsScreen.route) {
            val authorViewState by authorViewModel.authorsState
            AuthorsScreen(
                authorViewModel = authorViewModel,
                bookViewModel = bookViewModel,
                viewState = authorViewState,
                navController = navController,
                sharedPreferences = sharedPreferences,
                navigateToDetail = {
                    bookViewModel.fetchBooksByAuthor(it)
                    navController.navigate(Screen.HomeScreen.route)
                },
                searchByKeyword = {
                    authorViewModel.onSearchValueChange(it)
                    authorViewModel.fetchAuthorsByKeyword(it)
                }
            )
        }

        composable(Screen.UserScreen.route) {
            UserScreen(
                wishlistViewModel = wishlistViewModel,
                bookViewModel = bookViewModel,
                navController = navController,
                userViewModel = userViewModel,
                sharedPreferences = sharedPreferences,
                historyViewModel = historyViewModel
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                navController = navController,
                sharedPreferences = sharedPreferences
            )
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(
                navController = navController,
            )
        }
        composable(Screen.ChangeInfoScreen.route) {
            ChangeInfoScreen(
                navController = navController,
                sharedPreferences = sharedPreferences
            )
        }
        composable(Screen.ChangePassScreen.route) {
            ChangePassScreen(
                navController = navController,
                sharedPreferences = sharedPreferences
            )
        }
        composable(Screen.WishlistScreen.route) {
            wishlistViewModel.fetchWishlist(sharedPreferences.getToken()!!)
            val wishlistViewState by wishlistViewModel.wishlistState
            WishlistScreen(
                bookViewModel = bookViewModel,
                viewState = wishlistViewState,
                navController = navController,
                sharedPreferences = sharedPreferences,
                navigateToDetail = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("book", it)
                    navController.navigate(Screen.SingleBook.route)
                }
            )
        }
        composable(Screen.HistoryScreen.route) {
            historyViewModel.fetchHistory(sharedPreferences.getToken()!!)
            val historyViewState by historyViewModel.historyState
            HistoryScreen(
                navController = navController,
                sharedPreferences = sharedPreferences,
                viewState = historyViewState,
                openWebViewPdf = openWebViewPdf,
                historyViewModel = historyViewModel
            )
        }
    }
}