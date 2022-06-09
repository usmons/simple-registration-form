package io.usmon.registration.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.usmon.registration.coroutine.DefaultDispatchers
import io.usmon.registration.domain.use_case.LoginUserUseCase
import io.usmon.registration.domain.use_case.UseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(
    private val useCases: UseCases,
    private val dispatchers: DefaultDispatchers
) : ViewModel() {

    private var _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _loginUiEvent = Channel<LoginChannel>()
    val loginUiEvent: Flow<LoginChannel> = _loginUiEvent.receiveAsFlow()


    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.PhoneNumberChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _loginState.value = loginState.value.copy(
                        phoneNumber = event.phoneNumber
                    )
                }
            }
            is LoginUiEvent.PasswordChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _loginState.value = loginState.value.copy(
                        password = event.password
                    )
                }
            }
            is LoginUiEvent.Register -> {
                viewModelScope.launch(dispatchers.default) {
                    _loginUiEvent.send(LoginChannel.Register)
                }
            }
            is LoginUiEvent.Login -> {
                viewModelScope.launch(dispatchers.io) {
                    val result = useCases.loginUserUseCase(
                        phoneNumber = loginState.value.phoneNumber,
                        password = loginState.value.password
                    )
                    when (result) {
                        is LoginUserUseCase.Result.Error -> {
                            _loginUiEvent.send(LoginChannel.ShowSnackbar(result.message))
                        }
                        is LoginUserUseCase.Result.Success -> {
                            _loginUiEvent.send(LoginChannel.Success)
                        }
                    }
                }
            }
        }
    }

}