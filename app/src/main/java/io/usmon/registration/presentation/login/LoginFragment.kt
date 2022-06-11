package io.usmon.registration.presentation.login

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import io.usmon.registration.R
import io.usmon.registration.databinding.FragmentLoginBinding
import io.usmon.registration.presentation.base.BaseFragment
import io.usmon.registration.presentation.login.util.LoginChannel
import io.usmon.registration.presentation.login.util.LoginEvent
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
        collect(viewModel.loginEvent) { event ->
            when (event) {
                is LoginChannel.Register -> {
                    //TODO: navigate to register
                    findNavController().navigate(R.id.login_to_register)
                }
                is LoginChannel.Success -> {
                    //TODO: navigate to contacts list
                    findNavController().navigate(R.id.login_to_main)
                }
                is LoginChannel.ShowSnackbar -> {
                    Snackbar.make(
                        binding.root,
                        event.message.asString(requireContext()),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        binding.phoneNumber.onChangedListener {
            viewModel.onEvent(LoginEvent.PhoneNumberChanged(it))
        }

        binding.password.onChangedListener {
            viewModel.onEvent(LoginEvent.PasswordChanged(it))
        }

        binding.register.setOnClickListener {
            viewModel.onEvent(LoginEvent.Register)
        }

        binding.login.setOnClickListener {
            viewModel.onEvent(LoginEvent.Login)
        }
    }
}