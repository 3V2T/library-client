package eu.tutorials.mybookapp.View

import eu.tutorials.mybookapp.SharedPreferences.SharedPreferencesManager
import eu.tutorials.mybookapp.ViewModel.HistoryViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tutorials.mybookapp.R
import eu.tutorials.mybookapp.Model.data.Server

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(
    navController: NavController,
    sharedPreferences: SharedPreferencesManager,
    viewState: HistoryViewModel.HistoryState,
    openWebViewPdf: (path: String, title: String) -> Unit,
    historyViewModel: HistoryViewModel
) {
    val column1Weight = .2f
    val column2Weight = .3f
    val column3Weight = .25f
    val column4Weight = .25f
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(modifier = Modifier.fillMaxWidth() ,text = "History", textAlign = TextAlign.Center)
                },
                backgroundColor = colorResource(id = R.color.app_bar_color),
                contentColor = Color.White
            )
        }
    ) {
        LazyColumn(
            Modifier
                .padding(it)
                .background(color = colorResource(id = R.color.text_bar_color))
                .fillMaxSize())
        {
            item {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TableCell(
                        text = "Book",
                        weight = column2Weight,
                        alignment = TextAlign.Left,
                        title = true
                    )
                    TableCell(text = "Author", weight = column1Weight, title = true)
                    TableCell(text = "Date", weight = column3Weight, title = true)
                }
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                )
            }

            itemsIndexed(viewState.history) { _, event ->
                val pdfFilePath = "http://${Server.DEFAULT_PORT}${event.file_path}"
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            historyViewModel.addEventToHistory(sharedPreferences.getToken()!!, event.book_id)
                            openWebViewPdf(pdfFilePath, event.nameOfBook)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TableCell(
                        text = event.nameOfBook,
                        weight = column2Weight,
                        alignment = TextAlign.Left
                    )
                    TableCell(text = event.author, weight = column1Weight)
                    TableCell(
                        text = event.last_read,
                        weight = column3Weight,
                        alignment = TextAlign.Right
                    )
                }
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    alignment: TextAlign = TextAlign.Center,
    title: Boolean = false
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
            .padding(10.dp),
        fontWeight = if (title) FontWeight.Bold else FontWeight.Normal,
        textAlign = TextAlign.Center,
    )
}

data class Event(val nameOfBook: String, val author: String,val date: String)

val historyList = listOf(
    Event("Phía nam biên giới, phía tây mă trời", "Haruki Murakami","2024-05-26 09:48:56"),
    Event("Gió qua rặng liễu ", "Kenneth Grahame","2024-05-26 09:48:56"),
    Event("Sông Đông êm đềm", "Mikhail Aleksandrovich Sholokhov","2024-05-26 09:48:56"),
    Event("Anh em nhà karamazov", "Fyodor Mikhaylovich Dostoyevsky","2024-05-26 09:48:56"),
)

//@Preview
//@Composable
//fun HistoryScreenPreview() {
//    HistoryScreen()
//}