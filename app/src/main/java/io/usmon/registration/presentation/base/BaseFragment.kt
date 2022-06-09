package io.usmon.registration.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

// Created by Usmon Abdurakhmanv on 6/9/2022.

abstract class BaseFragment<VM : ViewModel, VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    protected abstract val viewModel: VM

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    protected open fun myCreate(savedStateBundle: Bundle?) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myCreate(savedInstanceState)
    }

    protected abstract fun myCreateView(savedInstanceState: Bundle?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater(inflater, container, false)
        if (_binding == null)
            throw IllegalArgumentException("ViewBinding couldn't be null")
        myCreateView(savedInstanceState)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}