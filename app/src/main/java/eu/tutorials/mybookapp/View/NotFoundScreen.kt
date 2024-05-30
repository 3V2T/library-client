package eu.tutorials.mybookapp.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.mybookapp.R

@Composable
fun NotFoundScreen(
) {
    Column(
        modifier = Modifier.fillMaxSize().background(colorResource(id = R.color.text_bar_color)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Book Not Found",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.app_bar_color)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Please try again",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.app_bar_color)
        )
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun NotFoundWishlistScreen(
) {
    Column(
        modifier = Modifier.fillMaxSize().background(colorResource(id = R.color.text_bar_color)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Your wishlist is empty",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.app_bar_color)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Please add some books",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.app_bar_color)
        )
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun NotFoundAuthorScreen(
) {
    Column(
        modifier = Modifier.fillMaxSize().background(colorResource(id = R.color.text_bar_color)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Author Not Found",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.app_bar_color)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Please try again",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.app_bar_color)
        )
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun NotFoundCategoryScreen(
) {
    Column(
        modifier = Modifier.fillMaxSize().background(colorResource(id = R.color.text_bar_color)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Category Not Found",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.app_bar_color)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Please try again",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.app_bar_color)
        )
        Spacer(Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun NotFoundScreenPreview() {
    NotFoundAuthorScreen()
}