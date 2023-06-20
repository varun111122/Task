package com.chiragtask.ui.dashboard


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.chiragtask.R
import com.chiragtask.base.BaseFragment
import com.chiragtask.databinding.FragmentDashBoardBinding
import com.chiragtask.db.UserData
import org.koin.android.ext.android.inject


class DashBoardFragment : BaseFragment<FragmentDashBoardBinding>(), View.OnClickListener {
    private val viewModel: DashboardViewModel by inject()
    lateinit var adapterDash: AdapterDash
    private var userList = ArrayList<UserData>()

    override fun getLayoutRes() = R.layout.fragment_dash_board

    override fun onApiRetry(apiCode: String) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        handleClicks()
        initRecyclerView()
        displayListOfUser()

    }

    private fun initRecyclerView() {
        adapterDash = AdapterDash(){
            findNavController().navigate(R.id.action_dashBoardFragment_to_weatherFragment)
        }
        binding.dashRecycler.adapter = adapterDash

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                viewModel.deleteSubscriber(userList[position])
                displayListOfUser()

//                recyclerDataArrayList.remove(viewHolder.adapterPosition)
//                recyclerViewAdapter.notifyItemRemoved(viewHolder.adapterPosition)

            }
        }).attachToRecyclerView(binding.dashRecycler)

    }

    private fun handleClicks() {
        binding.ivAdd.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivAdd -> {
                findNavController().navigate(R.id.action_dashBoardFragment_to_creatUserFragment)
            }
        }

    }


    private fun displayListOfUser() {
        viewModel.allUsers.observe(requireActivity(), Observer {

            userList = it as ArrayList<UserData>
            adapterDash.setList(it)
            adapterDash.notifyDataSetChanged()
        })
    }


}