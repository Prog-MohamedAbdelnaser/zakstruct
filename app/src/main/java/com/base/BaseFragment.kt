package com.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.base.dialogs.CustomProgressDialog
import com.base.extensions.getDrawableCompat
import com.zaka.base.extensions.hide
import com.zaka.databinding.FragmentBaseBinding

abstract  class BaseFragment : Fragment() {

    abstract fun inflateBinding(inflateId:View)

    lateinit var binding : FragmentBaseBinding

    lateinit var progressDialog: CustomProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //   inflater.inflate(R.layout.fragment_base, container, false)
        binding = FragmentBaseBinding.inflate(inflater, container, false)
        binding.stub.apply {

            setOnInflateListener { id, childView ->
                inflateBinding(id)
                onViewInflated(id, childView)
            }

        }
        return binding.root
    }
    override fun onViewCreated(parentView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(parentView, savedInstanceState)
        setHasOptionsMenu(true)
        progressDialog= CustomProgressDialog(requireActivity())


      binding.stub.  inflate()

    }

    open fun onViewInflated(parentView: View, childView: View) {
        initEventHandler()
        initModelObservers()
    }

    fun startActivityWithFading(intent: Intent){
        startActivity(intent)
        requireActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }

    open fun initModelObservers(){}

    open fun initEventHandler(){}

    protected fun hideProgress() {
           binding. loadingViewInc.loadingView.hide()
    }

    fun hideError(){
       binding.placeHolderLayoutInc. placeHolderLayout.hide()
    }

    protected fun showProgress() {
        binding.loadingViewInc.loadingView.hide()
        hideError()
    }

    protected fun showError(error: String) {
        binding.placeHolderLayoutInc.placeHolderLayout.visibility = View.VISIBLE
        binding.placeHolderLayoutInc.tvPlaceHolderMessage.text = error
    }


    fun showProgressDialog(){
        progressDialog.show()
    }

    fun hideProgressDialog(){
        progressDialog.dismiss()
    }


    fun setPlaceHolderResource(@DrawableRes placeHolderResourc:Int){
        binding.placeHolderLayoutInc.imgViewPlaceHolder.visibility=View.VISIBLE
           binding.placeHolderLayoutInc. imgViewPlaceHolder.setImageDrawable(requireContext().getDrawableCompat(placeHolderResourc))

    }
}