package io.usmon.registration.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.usmon.registration.coroutine.DefaultDispatchers
import io.usmon.registration.domain.use_case.LoginUserUseCase
import io.usmon.registration.domain.use_case.UseCases
import io.usmon.registration.presentation.login.util.LoginChannel
import io.usmon.registration.presentation.login.util.LoginEvent
import io.usmon.registration.presentation.login.util.LoginState
import io.usmon.registration.util.preferences.Preferences
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(
    private val useCases: UseCases,
    private val dispatchers: DefaultDispatchers,
    private val preferences: Preferences
) : ViewModel() {

    private var _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _loginEvent = Channel<LoginChannel>()
    val loginEvent: Flow<LoginChannel> = _loginEvent.receiveAsFlow()

    init {
        if (preferences.isUserAuthenticated()) {
            viewModelScope.launch(dispatchers.default) {
                _loginEvent.send(LoginChannel.Success)
            }
        }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.PhoneNumberChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _loginState.value = loginState.value.copy(
                        phoneNumber = event.phoneNumber
                    )
                }
            }
            is LoginEvent.PasswordChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _loginState.value = loginState.value.copy(
                        password = event.password
                    )
                }
            }
            is LoginEvent.Register -> {
                viewModelScope.launch(dispatchers.default) {
                    _loginEvent.send(LoginChannel.Register)
                }
            }
            is LoginEvent.Login -> {
                viewModelScope.launch(dispatchers.io) {
                    val result = useCases.loginUserUseCase(
                        phoneNumber = loginState.value.phoneNumber,
                        password = loginState.value.password
                    )
                    when (result) {
                        is LoginUserUseCase.Result.Error -> {
                            _loginEvent.send(LoginChannel.ShowSnackbar(result.message))
                        }
                        is LoginUserUseCase.Result.Success -> {
                            _loginEvent.send(LoginChannel.Success)
                        }
                    }
                }
            }
        }
    }

}