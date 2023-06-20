package com.chiragtask.ui.createUsers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.chiragtask.R
import com.chiragtask.ui.dashboard.DashRepo
import com.chiragtask.base.BaseFragment
import com.chiragtask.databinding.FragmentCreatUserBinding
import com.chiragtask.db.DashModelFactory
import com.chiragtask.db.UserDatabase


class CreatUserFragment : BaseFragment<FragmentCreatUserBinding>(), View.OnClickListener {
    lateinit var viewModel: CreateViewModel

    override fun getLayoutRes() = R.layout.fragment_creat_user

    override fun onApiRetry(apiCode: String) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = UserDatabase.getInstance(requireContext()).dao
        val repository = DashRepo(dao)
        val factory = DashModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[CreateViewModel::class.java]
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