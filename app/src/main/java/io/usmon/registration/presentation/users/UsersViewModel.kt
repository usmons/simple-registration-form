package io.usmon.registration.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.usmon.registration.coroutine.DefaultDispatchers
import io.usmon.registration.domain.use_case.UseCases
import io.usmon.registration.presentation.users.util.UsersChannel
import io.usmon.registration.presentation.users.util.UsersEvent
import io.usmon.registration.presentation.users.util.UsersState
import io.usmon.registration.util.preferences.Preferences
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UsersViewModel(
    private val useCases: UseCases,
    private val dispatchers: DefaultDispatchers,
    private val preferences: Preferences
) : ViewModel() {

    private val _usersState = MutableStateFlow(UsersState())
    val usersState: StateFlow<UsersState> = _usersState.asStateFlow()

    private val _usersEvent = Channel<UsersChannel>()
    val usersEvent: Flow<UsersChannel> = _usersEvent.receiveAsFlow()


    init {
        useCases.getAllUsers().onEach { users ->
            _usersState.value = _usersState.value.copy(
                users = users
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: UsersEvent) {
        when (event) {
            is UsersEvent.OpenDialog -> {
                viewModelScope.launch(dispatchers.default) {
                    _usersState.value = usersState.value.copy(
                        userOnDialog = event.user
                    )
                }
            }
            is UsersEvent.CloseDialog -> {
                viewModelScope.launch(dispatchers.default) {
                    _usersState.value = usersState.value.copy(
                        userOnDialog = null
                    )
                }
            }
            is UsersEvent.LogOut -> {
                viewModelScope.launch(dispatchers.io) {
                    preferences.clear()
                    _usersEvent.send(UsersChannel.LogOut)
                }
            }
        }
    }

}