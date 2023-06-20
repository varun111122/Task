package com.chiragtask.ui.dashboard

import com.chiragtask.R
import com.chiragtask.base.BaseViewHolder
import com.chiragtask.databinding.AdapterDashBinding
import com.chiragtask.base.BaseAdapter
import com.chiragtask.db.UserData

class AdapterDash(private val function: (UserData) -> Unit) : BaseAdapter<AdapterDashBinding>() {
    private val userList = ArrayList<UserData>()

    override fun getLayoutRes() = R.layout.adapter_dash

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        binding.tvUserName.text = "${userList[position].firstName} ${userList[position].lastName}"
        binding.tvUserEmail.text = userList[position].email

        binding.cd.setOnClickListener {
            function(userList[position])
        }

    }

    fun setList(list: List<UserData>) {
        userList.clear()
        userList.addAll(list)
    }
}