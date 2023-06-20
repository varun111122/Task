package com.chiragtask.ui.createUsers

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.chiragtask.R
import com.chiragtask.base.BaseFragment
import com.chiragtask.databinding.FragmentCreatUserBinding
import org.koin.android.ext.android.inject


class CreatUserFragment : BaseFragment<FragmentCreatUserBinding>(), View.OnClickListener {
    private val viewModel: CreateViewModel by inject()

    override fun getLayoutRes() = R.layout.fragment_creat_user

    override fun onApiRetry(apiCode: String) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        handleClick()
        eventHandleObserver()
    }

    private fun handleClick() {
        binding.ivBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBack -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun eventHandleObserver() {
        viewModel.eventState.observe(requireActivity(), Observer { observe ->
            observe.getContentIfNotHandled()?.let {
                showToastShort(it)
                findNavController().popBackStack()
            }
        })
    }


}