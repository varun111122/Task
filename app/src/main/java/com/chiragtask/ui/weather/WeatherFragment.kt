package com.chiragtask.ui.weather

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chiragtask.R
import com.chiragtask.base.BaseFragment
import com.chiragtask.data.prefrence.PreferenceDataStore
import com.chiragtask.databinding.FragmentWeatherBinding
import com.chiragtask.db.DashModelFactory
import com.chiragtask.db.UserDatabase
import com.chiragtask.ui.dashboard.DashRepo
import com.chiragtask.ui.dashboard.DashboardViewModel
import com.chiragtask.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch


class WeatherFragment : BaseFragment<FragmentWeatherBinding>(), View.OnClickListener {
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    private val weatherViewModel: WeatherViewModel by viewModels()
    lateinit var viewModel: DashboardViewModel


    override fun getLayoutRes() = R.layout.fragment_weather

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = UserDatabase.getInstance(requireContext()).dao
        val repository = DashRepo(dao)
        val factory = DashModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[DashboardViewModel::class.java]
        binding.lifecycleOwner = this

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        initUI()
    }
    public override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions()
            }
        }
        else {
//            getLastLocation()
        }
    }

//    private fun getLastLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        fusedLocationClient?.lastLocation?.addOnCompleteListener(this) { task ->
//            if (task.isSuccessful && task.result != null) {
//                lastLocation = task.result
//                latitudeText!!.text = latitudeLabel + ": " + (lastLocation)!!.latitude
//                longitudeText!!.text = longitudeLabel + ": " + (lastLocation)!!.longitude
//            }
//            else {
//                Log.d("varun", "getLastLocation:exception", task.exception)
//                showToastShort("No location detected. Make sure location is enabled on the device.")
//            }
//        }
//    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun initUI() {
        handleClick()
        eventHandleObserver()
//        weatherViewModel.getWeather()

    }

    override fun <T> onResponseSuccess(value: T, apiCode: String) {
        super.onResponseSuccess(value, apiCode)



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


    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }
    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showSnackbar("Location permission is needed for core functionality", "Okay",
                View.OnClickListener {
                    startLocationPermissionRequest()
                })
        }
        else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    // Permission granted.
//                    getLastLocation()
                }
                else -> {
                    showSnackbar("Permission was denied", "Settings",
                        View.OnClickListener {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts(
                                "package",
                                Build.DISPLAY, null
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
    companion object {
        private val TAG = "LocationProvider"
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }

    private fun showSnackbar(
        mainTextStringId: String, actionStringId: String,
        listener: View.OnClickListener
    ) {
        showToastShort(mainTextStringId)
    }

}