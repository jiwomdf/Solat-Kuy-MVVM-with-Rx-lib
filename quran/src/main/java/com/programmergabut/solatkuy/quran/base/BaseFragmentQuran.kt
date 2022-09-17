package com.programmergabut.solatkuy.quran.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.programmergabut.solatkuy.R
import com.programmergabut.solatkuy.databinding.LayoutErrorBottomsheetBinding
import com.programmergabut.solatkuy.util.SharedPrefUtil
import javax.inject.Inject

abstract class BaseFragmentQuran<VB: ViewBinding, VM: ViewModel>(
    private val layout: Int,
    private val viewModelClass: Class<VM>?,
    private val viewModelTest: VM?
) : Fragment(), LifecycleObserver {

    @Inject
    lateinit var sharedPrefUtil: SharedPrefUtil
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: VM
    protected val LOCATION_PERMISSIONS = 101

    abstract fun getViewBinding(): VB
    private var _binding: ViewBinding? = null
    protected val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        viewModelClass?.let {
            viewModel = viewModelTest ?: ViewModelProvider(requireActivity(), viewModelFactory).get(it)
        }
        return binding.root
    }

    protected fun isLocationPermissionGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    protected fun listLocationPermission(): Array<String> {
        return arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    protected fun <T : Any> gotoIntent(classIntent : Class<T>, bundle : Bundle? = null, isFinish : Boolean = false){
        val intent = Intent(this.activity, classIntent)
        if(bundle != null)
            intent.putExtras(bundle)
        startActivity(intent)
        if(isFinish)
            activity?.finish()
    }

    protected fun showBottomSheet(
        title : String = resources.getString(R.string.text_error_title),
        description : String = resources.getString(R.string.text_error_dsc),
        isCancelable : Boolean = true,
        isFinish : Boolean = false,
        callback: (() -> Unit)? = null) {

        val dialog =  BottomSheetDialog(requireContext())
        val dialogBinding = LayoutErrorBottomsheetBinding.inflate(layoutInflater)

        dialog.apply{
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setCancelable(isCancelable)
            setContentView(dialogBinding.root)
            setOnShowListener { dia ->
                val bottomSheetDialog = dia as BottomSheetDialog
                val bottomSheetInternal: FrameLayout =
                    bottomSheetDialog.findViewById(R.id.design_bottom_sheet)!!
                bottomSheetInternal.setBackgroundResource(R.drawable.bg_dark_rounded_top)
            }
        }
        dialogBinding.apply {
            tvTitle.text = title
            tvDesc.text = description
        }
        dialog.show()
        dialogBinding.btnOk.setOnClickListener {
            dialog.hide()
            callback?.invoke()
            if(isFinish)
                findNavController().popBackStack()
        }
    }

}