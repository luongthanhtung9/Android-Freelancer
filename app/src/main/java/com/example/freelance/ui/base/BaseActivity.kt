package com.example.freelance.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding

abstract class BaseActivity: AppCompatActivity() {

    abstract val viewModel: BaseViewModel
    protected open val binding: ViewBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (binding != null) {
            setContentView(binding?.root)
        }
        observerDefaultLiveData()
        initView()
        initListener()
        observerLiveData()

    }

    abstract fun initListener()

    abstract fun observerLiveData()

    abstract fun initView()

    private fun observerDefaultLiveData() {
        viewModel.apply {
    //            isLoading.observe(this@BaseActivity, Observer {
    //                handleShowLoading(it!!)
    //            })
            message.observe(this@BaseActivity, Observer {
    //                onError(it)
            })
            noInternetConnectionEvent.observe(this@BaseActivity, Observer {
    //                confirm.newBuild().setNotice(R.string.error_no_network)
            })
            connectTimeoutEvent.observe(this@BaseActivity, Observer {
    //                confirm.newBuild().setNotice(R.string.error_network_disconnect)
            })
            serverErrorEvent.observe(this@BaseActivity, Observer {
    //                confirm.newBuild().setNotice(R.string.error_at_server)
            })
            errorToHomeMesssage.observe(this@BaseActivity, Observer {
    //                confirm.newBuild().setNotice(R.string.error_at_server)
            })
            expireSessionEvent.observe(this@BaseActivity, Observer {
    //                confirm.newBuild().setNotice(it).addButtonRight {
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
            activeAnotherDeviceEvent.observe(this@BaseActivity, Observer {
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
            successFinishMessage.observe(this@BaseActivity, Observer {
    //                confirm.newBuild().setNotice(it).addButtonRight {
    //                    setResult(Activity.RESULT_OK)
    //                    finish()
    //                    confirm.dismiss()
    //                }.setAction(true)
            })
            errorFinishMessage.observe(this@BaseActivity, Observer {
    //                confirm.newBuild().setNotice(it).addButtonRight {
    //                    setResult(Activity.RESULT_CANCELED)
    //                    finish()
    //                    confirm.dismiss()
    //                }.setAction(true)
            })
            errorToHomeMesssage.observe(this@BaseActivity, Observer {
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
            invalidCertificateEvent.observe(this@BaseActivity, Observer {
                //                confirm.newBuild().setNotice(R.string.error_cerpinning)
            })
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (isFinishing ) {
            return super.dispatchTouchEvent(ev)
        }
        var end = false
        try {
            val currentFocus = currentFocus
            end = super.dispatchTouchEvent(ev)
            val currentTime = System.currentTimeMillis()

            if (currentFocus != null && (currentFocus is EditText)) {
                val w = getCurrentFocus() ?: return end
                val scrcoords = IntArray(2)
                w.getLocationOnScreen(scrcoords)
                val x = ev.rawX + w.left.toFloat() - scrcoords[0].toFloat()
                val y = ev.rawY + w.top.toFloat() - scrcoords[1].toFloat()
                if (ev.action == 1 && (x < w.left.toFloat() || x >= w.right.toFloat() || y < w.top.toFloat() || y > w.bottom.toFloat())) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)
                    w.clearFocus()
                }
            }
        } catch (e: Exception) {

        }

        return end
    }
}