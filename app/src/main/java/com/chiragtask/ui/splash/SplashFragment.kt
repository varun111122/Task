package com.chiragtask.ui.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chiragtask.R
import com.chiragtask.base.BaseFragment
import com.chiragtask.data.prefrence.PreferenceDataStore
import com.chiragtask.databinding.FragmentSplashBinding
import com.chiragtask.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override fun getLayoutRes() = R.layout.fragment_splash

    override fun onApiRetry(apiCode: String) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {

        lifecycleScope.launch {
            delay(2000)
            if (PreferenceDataStore.getBoolean(Constants.IS_LOGGED_IN) == true) {
                findNavController().navigate(R.id.action_splashFragment_to_dashBoardFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)

            }
        }
    }


}