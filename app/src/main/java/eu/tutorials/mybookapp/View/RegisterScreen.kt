package eu.tutorials.mybookapp.View

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun RegisterScreen(
    navController: NavController,
) {
    val coroutineScope = rememberCoroutineScope()
    var fullname by mutableStateOf("")
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPass by mutableStateOf("")
    Surface(
        color = colorResource(id = R.color.text_bar_color)
    ) {
        var registerInfo by remember { mutableStateOf(RegisterInfo()) }
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
                text = "Sign up",
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.app_bar_color),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            RegisterField(
                label = "Fullname",
                value = fullname,
                onChange = {
                    data ->
                    registerInfo = registerInfo.copy(name = data)
                    fullname = data
                },
                modifier = Modifier.fillMaxWidth()
            )
            RegisterField(
                label = "Username",
                value = username,
                onChange = {
                    data ->
                    registerInfo = registerInfo.copy(username = data)
                    username = data
                },
                modifier = Modifier.fillMaxWidth()
            )
            EmailField(
                value = email,
                onChange = {
                    data ->
                    registerInfo = registerInfo.copy(email = data)
                    email = data
                },
                modifier = Modifier.fillMaxWidth()
            )
            RegisterPasswordField(
                value = password,
                onChange = {
                    data ->
                    registerInfo = registerInfo.copy(password = data)
                    password = data
                           },
                submit = {
                    coroutineScope.launch {
                        registerSubmit(registerInfo, navController, context, confirmPass)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            ConfirmRegisterPasswordField(
                label = "Confirm Password",
                value = confirmPass,
                onChange = {
                    data ->
                    confirmPass = data
                },
                submit = {
                    coroutineScope.launch {
                        registerSubmit(registerInfo, navController, context, confirmPass)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        registerSubmit(registerInfo, navController, context, confirmPass)
                    }
                },
                enabled = registerInfo.isNotEmpty(),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.app_bar_color)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }
        }
    }
}

data class RegisterInfo(
    var name: String = "",
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var remember: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Register",
    placeholder: String = "Enter your info"
) {

    val focusManager = LocalFocusManager.current

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
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
fun EmailField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Email",
    placeholder: String = "Enter your Email"
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = { androidx.compose.material.Text(placeholder) },
        label = { androidx.compose.material.Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun RegisterPasswordField(
    value: String,
    onChange: (String) -> Unit,
    submit: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    placeholder: String = "Enter your Password"
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

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
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
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

@Composable
fun ConfirmRegisterPasswordField(
    value: String,
    onChange: (String) -> Unit,
    submit: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    placeholder: String = "Enter your Password"
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

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

suspend fun registerSubmit(
    registerInfo : RegisterInfo,
    navController: NavController,
    context: Context,
    confirmPass:String,
    userRepository: UserRepository = UserRepository(),
) {
    val text = "Đăng ký thất bại"
    val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(context, text, duration)
    coroutineScope {
        try {
            if(confirmPass != registerInfo.password) {
                val text = "Mật khẩu xác thực không chính xác"
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
            else {
                val response = userRepository.register(registerInfo)
                navController.navigate(Screen.LoginScreen.route)
            }
        } catch (e : Exception) {
            toast.show()
            println(e)
        }
    }
}