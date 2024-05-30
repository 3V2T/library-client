package eu.tutorials.mybookapp.Navigator

sealed class Screen(
    val route: String
) {
    object HomeScreen: Screen("home_screen")
    object SingleBook: Screen("single_book")
    object CategoriesScreen: Screen("categories_screen")
    object AuthorsScreen: Screen("authors_screen")
    object UserScreen: Screen("user_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object ChangeInfoScreen: Screen("changeInfo_screen")
    object ChangePassScreen: Screen("changePass_screen")
    object WishlistScreen: Screen("wishlist_screen")
    object HistoryScreen: Screen("history_screen")
}