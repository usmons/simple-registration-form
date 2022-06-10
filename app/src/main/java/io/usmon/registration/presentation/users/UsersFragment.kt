package io.usmon.registration.presentation.users

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.usmon.registration.R
import io.usmon.registration.databinding.BottomSheetBinding
import io.usmon.registration.databinding.FragmentUsersBinding
import io.usmon.registration.domain.model.User
import io.usmon.registration.presentation.MainActivity
import io.usmon.registration.presentation.base.BaseFragment
import io.usmon.registration.presentation.users.adapter.UsersListAdapter
import io.usmon.registration.presentation.users.util.UsersChannel
import io.usmon.registration.presentation.users.util.UsersEvent
import io.usmon.registration.util.extensions.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : BaseFragment<UsersViewModel, FragmentUsersBinding>(FragmentUsersBinding::inflate) {


    override val viewModel: UsersViewModel by viewModel()

    private lateinit var dialog: BottomSheetDialog

    override fun myCreateView(savedInstanceState: Bundle?) {

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        //We can use single lambda function on here, but given design is suck (you will find the design from README file)
        val usersListAdapter = UsersListAdapter { event ->
            when (event) {
                is UsersListAdapter.Event.UserClicked -> {
                    viewModel.onEvent(UsersEvent.OpenDialog(event.user))
                }
                is UsersListAdapter.Event.MoreClicked -> {
                    viewModel.onEvent(UsersEvent.OpenDialog(event.user))
                }
            }
        }

        binding.listViewUsers.adapter = usersListAdapter

        // UI state
        collect(viewModel.usersState) { usersState ->
            usersListAdapter.submitList(usersState.users)
            binding.usersDoesNotExist.isVisible = usersState.users.isEmpty()
            followDialog(usersState.userOnDialog)
        }

        // Channel
        collect(viewModel.usersEvent) { event ->
            when (event) {
                is UsersChannel.LogOut -> {
                    Intent(requireContext(), MainActivity::class.java).also {
                        startActivity(it)
                        requireActivity().finish()
                    }
                }
            }
        }
    }

    private fun followDialog(user: User?) {
        user?.let {
            val dialogBinding = BottomSheetBinding.inflate(layoutInflater).apply {
                fullName.text = it.fullName
                address.text = it.address
                it.image?.let { image.setImageBitmap(it) }
            }
            dialog = BottomSheetDialog(requireContext(), R.style.bottom_sheet)
            dialog.setContentView(dialogBinding.root)
            dialog.show()

            dialogBinding.phone.setOnClickListener {
                Toast.makeText(requireContext(), "Phone button has clicked!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            dialogBinding.message.setOnClickListener {
                Toast.makeText(requireContext(), "Message button has clicked!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            dialog.setOnDismissListener { viewModel.onEvent(UsersEvent.CloseDialog) }
        } ?: dialog.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.log_out_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                viewModel.onEvent(UsersEvent.LogOut)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}