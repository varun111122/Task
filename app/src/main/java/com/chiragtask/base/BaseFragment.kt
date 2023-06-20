package com.chiragtask.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.LayoutRes
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.chiragtask.data.network.ApiError
import com.chiragtask.data.network.EventObserver
import com.chiragtask.data.network.LiveDataObserver
import com.chiragtask.data.network.ToastData

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


abstract class BaseFragment<DB : ViewDataBinding> : Fragment(), LiveDataObserver {
    protected lateinit var binding: DB

    @LayoutRes
    abstract fun getLayoutRes(): Int
    abstract fun onApiRetry(apiCode: String)

    lateinit var baseActivity: BaseActivity
    var spokenTextLiveData = MutableLiveData<EventObserver<String>>()

//    private val startForResult = this.registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        if (result.resultCode == AppCompatActivity.RESULT_OK) {
//            val spokenText: String? =
//                result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
//                    .let { text -> text?.get(0) }
//            spokenTextLiveData.value = EventObserver(spokenText ?: "")
//        }
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d("LAGGING_ISSE", "onCreateView ")

        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = this
        return binding.root

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            baseActivity = context
            Log.d("LAGGING_ISSUE", "onAttach ")
        }
    }


    override fun <T> onResponseSuccess(value: T, apiCode: String) {
        baseActivity.lifecycleScope.launch {
            delay(150)
            baseActivity.runOnUiThread {
                hideLoading()
            }

        }
        Log.d("API_RESPONSE", "base response")

    }

    override fun onException(isNetworkAvailable: Boolean, exception: ApiError, apiCode: String) {
        Log.d("BaseFragment", "onException: ${exception.message}  $apiCode ")
        hideLoading()
        baseActivity.handleOnException(isNetworkAvailable, exception, apiCode)
    }


    override fun onError(error: ToastData, apiCode: String?) {
        hideLoading()
        baseActivity.handleOnError(error)
    }

    override fun onRetry(apiCode: String, networkError: Boolean, exception: ApiError) {
//        Log.d("BaseFragment", "onException: ${exception.message}  $apiCode ")
//        baseActivity.retryHandling(apiCode, networkError, {
//            onApiRetry(it)
//        }, exception)

    }

//    fun callMenu() {
//        val menuHost: MenuHost = baseActivity
//        menuHost.addMenuProvider(
//            this,
//            viewLifecycleOwner,
//            Lifecycle.State.RESUMED
//        )
//    }

    override fun onLoading(message: String, apiCode: String?) {
        Log.d("onHomeLoading", "onLoading")
        showLoading()
    }

    fun showLoading(message: String? = null) {
        baseActivity.showProgressBar(message)
    }

    fun hideLoading() {
        baseActivity.hideProgressBar()
    }


    fun showToastShort(message: String) {
        baseActivity.showToastShort(message)
    }

    fun showToastLong(message: String) {
        baseActivity.showToastLong(message)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("LAGGING_ISSE", "onDestroyView ")
        binding.unbind()
    }


    override fun onResume() {
        super.onResume()
        Log.d("LAGGING_ISSE", "onResume ")

    }

    override fun onPause() {
        super.onPause()
        Log.d("LAGGING_ISSE", "onPause ")

    }


}