package io.usmon.registration.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.usmon.registration.coroutine.DefaultDispatchers
import io.usmon.registration.domain.use_case.RegisterUserUseCase
import io.usmon.registration.domain.use_case.UseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val useCases: UseCases,
    private val dispatchers: DefaultDispatchers
) : ViewModel() {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    private val _registerUiEvent = Channel<RegisterChannel>()
    val registerUiEvent: Flow<RegisterChannel> = _registerUiEvent.receiveAsFlow()


    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.ImageChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _registerState.value = registerState.value.copy(
                        image = event.image
                    )
                }
            }
            is RegisterUiEvent.FullNameChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _registerState.value = registerState.value.copy(
                        fullName = event.fullName
                    )
                }
            }
            is RegisterUiEvent.PhoneNumberChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _registerState.value = registerState.value.copy(
                        phoneNumber = event.phoneNumber
                    )
                }
            }
            is RegisterUiEvent.CountryChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _registerState.value = registerState.value.copy(
                        country = event.country - 1
                    )
                }
            }
            is RegisterUiEvent.AddressChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _registerState.value = registerState.value.copy(
                        address = event.address
                    )
                }
            }
            is RegisterUiEvent.PasswordChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _registerState.value = registerState.value.copy(
                        password = event.password
                    )
                }
            }
            is RegisterUiEvent.Register -> {
                viewModelScope.launch(dispatchers.io) {
                    val result = useCases.registerUserUseCase(
                        fullName = registerState.value.fullName,
                        phoneNumber = registerState.value.phoneNumber,
                        country = registerState.value.country,
                        address = registerState.value.address,
                        password = registerState.value.password,
                        image = registerState.value.image
                    )
                    when (result) {
                        is RegisterUserUseCase.Result.Error -> {
                            _registerUiEvent.send(
                                RegisterChannel.ShowSnackbar(result.message)
                            )
                        }
                        is RegisterUserUseCase.Result.Success -> {
                            _registerUiEvent.send(
                                RegisterChannel.Success
                            )
                        }
                    }
                }
            }
        }
    }


}