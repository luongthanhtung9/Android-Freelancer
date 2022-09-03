package com.example.freelance.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding

abstract class BaseFragment: Fragment() {
    abstract val viewModel: BaseViewModel
    protected open val binding: ViewBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initView()
        observerLiveData()
        observerDefaultLiveData()
    }

    private fun observerDefaultLiveData() {
        viewModel.apply {
            //            isLoading.observe(viewLifecycleOwner, Observer {
            //                handleShowLoading(it!!)
            //            })
            message.observe(viewLifecycleOwner, Observer {
                //                onError(it)
            })
            noInternetConnectionEvent.observe(viewLifecycleOwner, Observer {
                //                confirm.newBuild().setNotice(R.string.error_no_network)
            })
            connectTimeoutEvent.observe(viewLifecycleOwner, Observer {
                //                confirm.newBuild().setNotice(R.string.error_network_disconnect)
            })
            serverErrorEvent.observe(viewLifecycleOwner, Observer {
                //                confirm.newBuild().setNotice(R.string.error_at_server)
            })
            errorToHomeMesssage.observe(viewLifecycleOwner, Observer {
                //                confirm.newBuild().setNotice(R.string.error_at_server)
            })
            expireSessionEvent.observe(viewLifecycleOwner, Observer {
                //                confirm.newBuild().setNotice(it).addButtonRight {
                //                    startActivity(
                //                        Intent(viewLifecycleOwner, HomeActivity::class.java).addFlags(
                //                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                //                        )
                //                    )
                //                    ActivityCompat.finishAffinity(this@BaseActivity)
                //                    setResult(Activity.RESULT_OK)
                //                    finish()
                //                    confirm.dismiss()
                //                }.setAction(true)
            })
            activeAnotherDeviceEvent.observe(viewLifecycleOwner, Observer {
                //                confirm.newBuild().setNotice(it).addButtonRight {
                //                    AppData.g().onClearData()
                //                    startActivity(
                //                        Intent(this@BaseActivity, HomeActivity::class.java).addFlags(
                //                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                //                        )
                //                    )
                //                    ActivityCompat.finishAffinity(this@BaseActivity)
                //                    setResult(Activity.RESULT_OK)
                //                    finish()
                //                    confirm.dismiss()
                //                }.setAction(true)
            })
            successFinishMessage.observe(viewLifecycleOwner, Observer {
                //                confirm.newBuild().setNotice(it).addButtonRight {
                //                    setResult(Activity.RESULT_OK)
                //                    finish()
                //                    confirm.dismiss()
                //                }.setAction(true)
            })
            errorFinishMessage.observe(viewLifecycleOwner, Observer {
                //                confirm.newBuild().setNotice(it).addButtonRight {
                //                    setResult(Activity.RESULT_CANCELED)
                //                    finish()
                //                    confirm.dismiss()
                //                }.setAction(true)
            })
            errorToHomeMesssage.observe(viewLifecycleOwner, Observer {
                //                confirm.newBuild().setNotice(it).addButtonRight {
                //                    setResult(Activity.RESULT_CANCELED)
                //                    startActivity(
                //                        Intent(this@BaseActivity, HomeActivity::class.java).addFlags(
                //                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                //                        )
                //                    )
                //                    ActivityCompat.finishAffinity(this@BaseActivity)
                //                    finish()
                //                    confirm.dismiss()
                //                }.setAction(true)
            })
            invalidCertificateEvent.observe(viewLifecycleOwner, Observer {
                //                confirm.newBuild().setNotice(R.string.error_cerpinning)
            })
        }
    }

    abstract fun initListener()

    abstract fun observerLiveData()

    abstract fun initView()
}