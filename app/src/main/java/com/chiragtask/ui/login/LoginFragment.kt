package com.chiragtask.ui.login

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.chiragtask.R
import com.chiragtask.base.BaseFragment
import com.chiragtask.data.prefrence.PreferenceDataStore
import com.chiragtask.databinding.FragmentLoginBinding
import com.chiragtask.utils.Constants
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val viewModel: LoginViewModel by viewModels()

    override fun getLayoutRes() = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        eventHandleObserver()


        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finishAffinity()

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            requireActivity(),
            onBackPressedCallback
        )


    }


    private fun eventHandleObserver() {
        viewModel.eventState.observe(requireActivity(), Observer { observe ->
            observe.getContentIfNotHandled()?.let {
                if (it == "login") {

                    findNavController().navigate(R.id.action_loginFragment_to_dashBoardFragment)
                } else {
                    showToastShort(it)
                }

            }
        })
    }


    override fun onApiRetry(apiCode: String) {
    }


}