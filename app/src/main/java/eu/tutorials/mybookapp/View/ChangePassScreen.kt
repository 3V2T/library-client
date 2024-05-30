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
fun ChangePassScreen(
    sharedPreferences: SharedPreferencesManager,
    navController: NavController,
) {
    val coroutineScope = rememberCoroutineScope()
    var newPassword by mutableStateOf("")
    var confirmNewPassword by mutableStateOf("")
    var currentpassword by mutableStateOf("")
    Surface(
        color = colorResource(id = R.color.text_bar_color)
    ) {
        var credentialPass by remember { mutableStateOf(CredentialPass()) }
        val context = LocalContext.current
        sharedPreferences.getToken()?.let { Log.i("Token", it) }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            RegisterPasswordField(
                placeholder = "Enter your new password",
                label = "New Password",
                value = newPassword,
                onChange = {
                        data ->
                    credentialPass = credentialPass.copy(newpassword = data)
                    newPassword = data
                },
                submit = {
                    coroutineScope.launch {
                        onSubmitNewPass(credentialPass, navController, context, sharedPreferences.getToken()!!, confirmNewPassword)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            RegisterPasswordField(
                placeholder = "Confirm your new password",
                label = "Confirm new password",
                value = confirmNewPassword,
                onChange = {
                    data ->
                    confirmNewPassword = data
                },
                submit = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            RegisterPasswordField(
                placeholder = "Enter your current password",
                label = "Your current password",
                value = currentpassword,
                onChange = {
                        data ->
                    credentialPass = credentialPass.copy(password = data)
                    currentpassword = data
                },
                submit = {
                    coroutineScope.launch {
                        onSubmitNewPass(credentialPass, navController, context, sharedPreferences.getToken()!!, confirmNewPassword)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        onSubmitNewPass(credentialPass, navController, context, sharedPreferences.getToken()!!, confirmNewPassword)
                    }
                },
                enabled = credentialPass.isNotEmpty(),
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

data class CredentialPass(
    var password: String = "",
    var newpassword: String = "",
) {
    fun isNotEmpty(): Boolean {
        return password.isNotEmpty() && newpassword.isNotEmpty()
    }
}

suspend fun onSubmitNewPass(
    credentialPass: CredentialPass,
    navController: NavController,
    context: Context,
    token: String,
    confirmPass: String,
    userRepository: UserRepository = UserRepository()
) {
    var text = "Thay đổi mật khẩu thất bại"
    val duration = Toast.LENGTH_SHORT
    var toast = Toast.makeText(context, text, duration)
    if(confirmPass != credentialPass.newpassword) {
        toast = Toast.makeText(context, "Mật khẩu xác thực không chính xác",Toast.LENGTH_SHORT)
        toast.show()
    }
    else {
        coroutineScope {
            try {
                val response = userRepository.changePass(credentialPass, token)
                navController.navigate(Screen.UserScreen.route)
                Toast.makeText(context, "Thay đổi mật khẩu thành công",Toast.LENGTH_SHORT)
            } catch (e : Exception) {
                toast.show()
                println(e)
            }
        }
    }

}