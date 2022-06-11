package io.usmon.registration.presentation.register.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import io.usmon.registration.databinding.SpinnerItemBinding

// Created by Usmon Abdurakhmanv on 6/10/2022.
class CountrySpinnerAdapter(
    private val values: Array<String>,
    private val defaultHint: String = "Choose..."
) : BaseAdapter() {

    override fun getCount(): Int = values.size + 1

    override fun getItem(position: Int): String {
        return if (position == 0) defaultHint else values[position - 1]
    }

    override fun getItemId(position: Int): Long {
        return View.generateViewId().toLong()
    }

    @SuppressLint("ViewHolder", "ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return SpinnerItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false).apply {
            text.text = if (position == 0) defaultHint else values[position - 1]
        }.root
    }

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }
}

