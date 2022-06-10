package io.usmon.registration.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.usmon.registration.coroutine.DefaultDispatchers
import io.usmon.registration.domain.use_case.RegisterUserUseCase
import io.usmon.registration.domain.use_case.UseCases
import io.usmon.registration.presentation.register.util.RegisterChannel
import io.usmon.registration.presentation.register.util.RegisterEvent
import io.usmon.registration.presentation.register.util.RegisterState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val useCases: UseCases,
    private val dispatchers: DefaultDispatchers
) : ViewModel() {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    private val _registerEvent = Channel<RegisterChannel>()
    val registerEvent: Flow<RegisterChannel> = _registerEvent.receiveAsFlow()


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.ImageChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _registerState.value = registerState.value.copy(
                        image = event.image
                    )
                }
            }
            is RegisterEvent.FullNameChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _registerState.value = registerState.value.copy(
                        fullName = event.fullName
                    )
                }
            }
            is RegisterEvent.PhoneNumberChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _registerState.value = registerState.value.copy(
                        phoneNumber = event.phoneNumber
                    )
                }
            }
            is RegisterEvent.CountryChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _registerState.value = registerState.value.copy(
                        country = event.country - 1
                    )
                }
            }
            is RegisterEvent.AddressChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _registerState.value = registerState.value.copy(
                        address = event.address
                    )
                }
            }
            is RegisterEvent.PasswordChanged -> {
                viewModelScope.launch(dispatchers.default) {
                    _registerState.value = registerState.value.copy(
                        password = event.password
                    )
                }
            }
            is RegisterEvent.Register -> {
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
                            _registerEvent.send(
                                RegisterChannel.ShowSnackbar(result.message)
                            )
                        }
                        is RegisterUserUseCase.Result.Success -> {
                            _registerEvent.send(
                                RegisterChannel.Success
                            )
                        }
                    }
                }
            }
        }
    }


}