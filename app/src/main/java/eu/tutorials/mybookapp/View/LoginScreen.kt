package eu.tutorials.mybookapp.View

import eu.tutorials.mybookapp.SharedPreferences.SharedPreferencesManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import eu.tutorials.mybookapp.R
import eu.tutorials.mybookapp.Navigator.Screen
import eu.tutorials.mybookapp.Model.repository.UserRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    sharedPreferences: SharedPreferencesManager,
    navController: NavController,
    ) {
    val coroutineScope = rememberCoroutineScope()
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    Surface(
        color = colorResource(id = R.color.text_bar_color)
    ) {
        var credentials by remember { mutableStateOf(Credentials()) }
        val context = LocalContext.current
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Sign in",
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.app_bar_color),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            LoginField(
                value = username,
                onChange = {
                    data ->
                    credentials = credentials.copy(username = data)
                    username = data },
                modifier = Modifier.fillMaxWidth()
            )
            PasswordField(
                value = password,
                onChange = {
                    data ->
                    credentials = credentials.copy(password = data)
                    password = data },
                submit = {
                    coroutineScope.launch {
                        checkCredential(credentials, context, navController, sharedPreferences)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        checkCredential(credentials, context, navController, sharedPreferences)
                    }
                },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.app_bar_color)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        }
    }
}


data class Credentials(
    var username: String = "",
    var password: String = "",
    var remember: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }
}


@Composable
fun LabeledCheckbox(
    label: String,
    onCheckChanged: () -> Unit,
    isChecked: Boolean
) {

    Row(
        Modifier
            .clickable(
                onClick = onCheckChanged
            )
            .padding(4.dp)
    ) {
        Checkbox(checked = isChecked, onCheckedChange = null)
        Spacer(Modifier.size(6.dp))
        Text(label)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Login",
    placeholder: String = "Enter your Login"
) {

    val focusManager = LocalFocusManager.current
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    submit: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    placeholder: String = "Enter your Password"
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    val leadingIcon = @Composable {
        androidx.compose.material3.Icon(
            Icons.Default.Key,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            androidx.compose.material3.Icon(
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }


    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = { submit() }
        ),
        placeholder = { androidx.compose.material.Text(placeholder) },
        label = { androidx.compose.material.Text(label) },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

suspend fun checkCredential(
    credentials: Credentials,
    context: Context,
    navController: NavController,
    sharedPreferences: SharedPreferencesManager,
    userRepository: UserRepository = UserRepository()
) {
    val text = "Đăng nhập thất bại"
    val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(context, text, duration)
    coroutineScope {
        try {
            val response = userRepository.login(credentials)
            val user = response.user
            sharedPreferences.saveSpLoginStatus(true)
            sharedPreferences.saveSpName("spName", user.username)
            sharedPreferences.saveSpToken("spToken", user.accessToken)
            navController.navigate(Screen.UserScreen.route)
        } catch (e : Exception) {
            toast.show()
            println(e)
        }
    }
}
