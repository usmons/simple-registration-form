package io.usmon.registration.presentation.login

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import io.usmon.registration.databinding.FragmentLoginBinding
import io.usmon.registration.presentation.base.BaseFragment
import io.usmon.registration.util.extensions.collect
import io.usmon.registration.util.extensions.onChangedListener
import io.usmon.registration.util.extensions.update
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override val viewModel: LoginViewModel by viewModel()

    override fun myCreateView(savedInstanceState: Bundle?) {

        // State
        collect(viewModel.loginState) { state ->
            binding.phoneNumber.update(state.phoneNumber)
            binding.password.update(state.password)
        }

        // UI event
        collect(viewModel.loginUiEvent) { event ->
            when (event) {
                is LoginChannel.Register -> {
                    //TODO: navigate to register
                }
                is LoginChannel.Success -> {
                    //TODO: navigate to contacts list
                }
                is LoginChannel.ShowSnackbar -> {
                    Snackbar.make(
                        binding.root,
                        event.message.asString(requireContext()),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.phoneNumber.onChangedListener {
            viewModel.onEvent(LoginUiEvent.PhoneNumberChanged(it))
        }

        binding.password.onChangedListener {
            viewModel.onEvent(LoginUiEvent.PasswordChanged(it))
        }

        binding.register.setOnClickListener {
            viewModel.onEvent(LoginUiEvent.Register)
        }

        binding.login.setOnClickListener {
            viewModel.onEvent(LoginUiEvent.Login)
        }
    }
}