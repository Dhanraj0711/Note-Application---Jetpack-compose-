package com.example.noteapp.presentation.login

import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.noteapp.feature_note.data.data_source.ApiService
import com.example.noteapp.presentation.util.Screen
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(), targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000
            )
        )
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5)
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}

@Composable
fun LoginScreen(navController: NavController, window: Window) {
    val myViewModel: LoginViewModel = hiltViewModel()
    val e = myViewModel.email.value
    val p = myViewModel.password.value
    val c = LocalContext.current
    LaunchedEffect(key1 = true) {
        myViewModel.login.collectLatest { event ->
            when (event) {
                is UserLoginEvent.SaveUser -> {
                    navController.navigate(Screen.NotesScreen.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                    Toast.makeText(c, event.j.toString(), Toast.LENGTH_SHORT).show()
                }
                is UserLoginEvent.ShowaSnack -> {
                    Toast.makeText(c, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {

        Row(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .shimmerEffect(), text = ""
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .shimmerEffect(), text = ""
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = e.text, onValueChange = {
            myViewModel.event(LoginEvent.EString(it))
        }, label = { Text(text = e.hint) })
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = p.text, onValueChange = {
            myViewModel.event(LoginEvent.PString(it))
        }, label = { Text(text = p.hint) })
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { myViewModel.event(LoginEvent.UserLogin) }) { Text(text = "Login") }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@HiltViewModel
class LoginViewModel @Inject constructor(private val authUserCase: AuthUserCase) : ViewModel() {

    val _userLogin = MutableSharedFlow<UserLoginEvent>()
    val login = _userLogin.asSharedFlow()

    val _email = mutableStateOf(TextFieldState(hint = "E-mail..."))
    val _password = mutableStateOf(TextFieldState(hint = "Password..."))

    val email: State<TextFieldState> = _email
    val password: State<TextFieldState> = _password

    fun event(event: LoginEvent) {
        when (event) {
            is LoginEvent.EString -> {
                _email.value = email.value.copy(text = event.e)
            }
            is LoginEvent.Efocus -> {
                _email.value = email.value.copy(hintVisible = !event.e.isFocused && email.value.text.isBlank())
            }
            is LoginEvent.PString -> {
                _password.value = password.value.copy(text = event.p)
            }
            is LoginEvent.Pfocus -> {
                _email.value = email.value.copy(hintVisible = !event.p.isFocused && email.value.text.isBlank())
            }
            is LoginEvent.UserLogin -> {
                viewModelScope.launch {
                    try {
                        authUserCase.login(
                            email.value.text, password.value.text,
                            "ANDROID", "SDDSD", "SDJKSJD"
                        ).collect {
                            _userLogin.emit(UserLoginEvent.SaveUser(it.data!!))
                            _userLogin.emit(UserLoginEvent.ShowaSnack(message = it.toString()))
                            Log.e("login", "login::$it")
                        }
                    } catch (e: Exception) {
                        Log.e("DDDDDDD",""+e.toString())
                        _userLogin.emit(
                            UserLoginEvent.ShowaSnack(
                                message = ("dddd::::" + e.message) ?: "Unknown error"
                            )
                        )
                    }
                }
            }
        }
    }
}

data class TextFieldState(
    val text: String = "", val hint: String = "", val hintVisible: Boolean = true,
)

sealed class LoginEvent {
    data class EString(val e: String) : LoginEvent()
    data class PString(val p: String) : LoginEvent()

    data class Efocus(val e: FocusState) : LoginEvent()
    data class Pfocus(val p: FocusState) : LoginEvent()

    object UserLogin : LoginEvent()
}

data class AuthUserCase(
    val login: LoginUser,
)

class MyRepository(private val apiService: ApiService) : BaseApiResponse() {
    suspend fun login(
        email: String, password: String, os: String, d_token: String, uuID: String,
    ) = flow {
        emit(safeApiCall { apiService.userLogin(email, password, os, d_token, uuID) })
    }.flowOn(Dispatchers.IO)
}


sealed class UserLoginEvent {
    data class ShowaSnack(val message: String) : UserLoginEvent()
    data class SaveUser(val j: JsonObject) : UserLoginEvent()
}


class LoginUser(private val myRepository: MyRepository) {
    suspend operator fun invoke(
        email: String, password: String,
        os: String, d_token: String, uuID: String,
    ): Flow<NetworkResult<JsonObject>> = myRepository.login(email, password, os, d_token, uuID)
}

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
    class Loading<T> : NetworkResult<T>()
}

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")
}
