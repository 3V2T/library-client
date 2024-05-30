package eu.tutorials.mybookapp.View

import eu.tutorials.mybookapp.SharedPreferences.SharedPreferencesManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tutorials.mybookapp.R
import eu.tutorials.mybookapp.Navigator.Screen
import eu.tutorials.mybookapp.Model.repository.UserRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun ChangeInfoScreen(
    sharedPreferences: SharedPreferencesManager,
    navController: NavController,
) {
    val coroutineScope = rememberCoroutineScope()
    var fullname by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    Surface(
        color = colorResource(id = R.color.text_bar_color)
    ) {
        var info by remember { mutableStateOf(Info()) }
        val context = LocalContext.current
        sharedPreferences.getToken()?.let { Log.i("Token", it) }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            RegisterField(
                label = "Fullname",
                value = fullname,
                onChange = {
                    data ->
                    info = info.copy(name = data)
                    fullname = data
                },
                modifier = Modifier.fillMaxWidth()
            )
            EmailField(
                value = email,
                onChange = {
                    data ->
                    info = info.copy(email = data)
                    email = data
                },
                modifier = Modifier.fillMaxWidth()
            )
            RegisterPasswordField(
                value = password,
                onChange = {
                    data ->
                    info = info.copy(password = data)
                    password = data
                },
                submit = {
                    coroutineScope.launch {
                        onSubmit(info, navController, context, sharedPreferences.getToken()!!)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        onSubmit(info, navController, context, sharedPreferences.getToken()!!)
                    }
                },
                enabled = info.isNotEmpty(),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.app_bar_color)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}

data class Info(
    var name: String = "",
    var email: String = "",
    var password: String = "",
) {
    fun isNotEmpty(): Boolean {
        return password.isNotEmpty() && (name.isNotEmpty() || email.isNotEmpty())
    }
}

suspend fun onSubmit(
    info: Info,
    navController: NavController,
    context: Context, token: String,
    userRepository: UserRepository = UserRepository()
) {
    val text = "Thay đổi thông tin thất bại"
    val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(context, text, duration)
    coroutineScope {
        try {
            val response = userRepository.changeInfo(info, token)
            navController.navigate(Screen.UserScreen.route)
        } catch (e : Exception) {
           toast.show()
            println(e)
        }
    }
}