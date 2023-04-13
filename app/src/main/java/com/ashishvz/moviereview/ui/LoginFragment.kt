package com.ashishvz.moviereview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ashishvz.moviereview.R
import com.ashishvz.moviereview.databinding.FragmentLoginBinding
import com.ashishvz.moviereview.utility.Utility
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginButton.setOnClickListener {
                if (userNameEditText.text.isNullOrEmpty() || !Utility.isEmailValid(userNameEditText.text.toString())) {
                    Snackbar.make(binding.root, "Username is empty or invalid", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (passwordEditText.text.isNullOrEmpty()) {
                    Snackbar.make(binding.root, "Password is empty", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMovieFragment())
            }
        }
    }
}