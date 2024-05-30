package eu.tutorials.mybookapp

import eu.tutorials.mybookapp.SharedPreferences.SharedPreferencesManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rajat.pdfviewer.PdfViewerActivity
import com.rajat.pdfviewer.util.saveTo
import eu.tutorials.mybookapp.Navigator.Navigation
import eu.tutorials.mybookapp.ui.theme.MyBookAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBookAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = applicationContext
                    val sharedPreferences = SharedPreferencesManager(context)
                    sharedPreferences.clearSession()
                    Navigation(
                        openWebViewPdf = {
                            pdfFilePath, title ->
                            startActivity(
                                if(sharedPreferences.isLogin()) {
                                    PdfViewerActivity.launchPdfFromUrl(
                                        context = this,
                                        pdfUrl = pdfFilePath,
                                        pdfTitle = title,
                                        saveTo = saveTo.ASK_EVERYTIME,
                                        enableDownload = true
                                    )
                                } else {
                                    PdfViewerActivity.launchPdfFromUrl(
                                        context = this,
                                        pdfUrl = pdfFilePath,
                                        pdfTitle = title,
                                        saveTo = saveTo.ASK_EVERYTIME,
                                        enableDownload = false
                                    )
                                }
                            )
                        },
                        sharedPreferences = sharedPreferences
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun MyComposablePreview() {
//    Navigation()
//}

