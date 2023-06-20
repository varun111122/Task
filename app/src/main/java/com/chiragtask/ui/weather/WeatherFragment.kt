package com.chiragtask.ui.weather

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.apiModel.Weather
import com.apiModel.WeatherModel
import com.chiragtask.R
import com.chiragtask.base.BaseFragment
import com.chiragtask.data.prefrence.PreferenceDataStore
import com.chiragtask.databinding.FragmentWeatherBinding
import com.chiragtask.ui.dashboard.DashboardViewModel
import com.chiragtask.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class WeatherFragment : BaseFragment<FragmentWeatherBinding>(), View.OnClickListener {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val weatherViewModel: WeatherViewModel by inject()
    private val viewModel: DashboardViewModel by inject()


    override fun getLayoutRes() = R.layout.fragment_weather

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        weatherViewModel.getApiResponse().observe(viewLifecycleOwner, this)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        initUI()
    }


    private fun initUI() {
        handleClick()
        eventHandleObserver()
        weatherViewModel.getWeather()


    }


    override fun <T> onResponseSuccess(value: T, apiCode: String) {
        super.onResponseSuccess(value, apiCode)

        (value as WeatherModel).let {
            binding.tvLocation.text = "TimeZone is ${it.timezone}"
            binding.tvTemp.text = "Today Temprature : ${it.current.temp}"
            binding.tvHumidity.text = "Today Humidity : ${it.current.humidity}"
            binding.tvWeatherType.text = "Today Weather Type : ${it.current.weather[0].main}"
            binding.tvWindSpeed.text = "Today wind speed : ${it.current.wind_speed}"
        }


    }

    private fun handleClick() {
        binding.ivBack.setOnClickListener(this)
        binding.ivLogout.setOnClickListener(this)
    }

    override fun onApiRetry(apiCode: String) {
    }

    private fun eventHandleObserver() {
        viewModel.eventState.observe(requireActivity(), Observer { observe ->
            observe.getContentIfNotHandled()?.let {

                showToastShort(it)
                lifecycleScope.launch {
                    PreferenceDataStore.saveBoolean(Constants.IS_LOGGED_IN, false)
                }

                findNavController().navigate(R.id.action_weatherFragment_to_loginFragment)

            }
        })
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBack -> {
                findNavController().popBackStack()

            }

            R.id.ivLogout -> {

                viewModel.clearAllSubscriber()

            }
        }
    }


}