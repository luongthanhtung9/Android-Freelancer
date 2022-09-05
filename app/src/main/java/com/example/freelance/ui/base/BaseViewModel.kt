package com.example.freelance.ui.base

import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freelance.network.ServerResponseEntity
import com.example.freelance.network.convertToBaseException
import kotlinx.coroutines.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLPeerUnverifiedException

abstract class BaseViewModel: ViewModel() {
    val message = MutableLiveData<String>()
    val noInternetConnectionEvent = MutableLiveData<Unit>()
    val connectTimeoutEvent = MutableLiveData<Unit>()
    val invalidCertificateEvent = MutableLiveData<Unit>()
    val serverErrorEvent = MutableLiveData<Unit>()
    val successFinishMessage = MutableLiveData<String>()
    val expireSessionEvent = MutableLiveData<String>()
    val activeAnotherDeviceEvent = MutableLiveData<String>()
    val errorFinishMessage = MutableLiveData<String>()
    val errorToHomeMesssage = MutableLiveData<String>()

    @CallSuper
    override fun onCleared() {
        super.onCleared()
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                when (throwable) {
                    is UnknownHostException -> {
//                        noInternetConnectionEvent.call()
                    }
                    is SocketTimeoutException -> {
//                        connectTimeoutEvent.call()
                    }
                    is SSLPeerUnverifiedException -> {
//                        invalidCertificateEvent.call()
                    }
                    else -> {
                        val baseException = convertToBaseException(throwable)
                        if ("87" == baseException.code) {//return
                            expireSessionEvent.value = baseException.serverErrorResponse?.des
                            return@withContext
                        }
                        //Login trên thiết bị khác
                        if ("86" == baseException.code) {//return
                            activeAnotherDeviceEvent.value = baseException.serverErrorResponse?.des
                            return@withContext
                        }
                        if (baseException.serverErrorResponse == null) {
//                            serverErrorEvent.call()
                        }
                        onError(
                            baseException.code,
                            baseException.serverErrorResponse
                        )
                    }
                }
            }
        }
    }


    open fun onError(code: String?, source: ServerResponseEntity?) {
        if (!source?.message.isNullOrEmpty())
            message.value = source?.message
        else {
//            serverErrorEvent.call()
        }
    }

    fun hideLoading() {
//        isLoading.value = false
    }

    fun showLoading() {
//        isLoading.value = true
    }

    val network = viewModelScope + exceptionHandler


    var listBlock = arrayListOf<suspend CoroutineScope.() -> Unit>()

    fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        showLoading()

        listBlock.add(block)

        return network.launch {
            try {
                block.invoke(network)
            } catch (e: Exception) {
                Log.wtf("EX", e)
                throw e
            } finally {
                listBlock.remove(block)
                if (listBlock.isEmpty())
                    withContext(Dispatchers.Main) {
                        hideLoading()
                    }
            }

            return@launch
        }
    }

    fun launchBackground(block: suspend CoroutineScope.() -> Unit): Job {
        return network.launch {
            try {
                block.invoke(network)
            } catch (e: Exception) {
                Log.wtf("EX", e)
                throw e
            }
            return@launch
        }
    }

    fun launchSilent(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch {
            try {
                block.invoke(viewModelScope)
            } catch (e: Exception) {
                Log.wtf("EX", e)
            }
            return@launch
        }
    }

    fun launchIO(block: suspend CoroutineScope.() -> Unit): Job {
        showLoading()

        listBlock.add(block)

        return network.launch(Dispatchers.IO) {
            try {
                block.invoke(network)
            } catch (e: Exception) {
                Log.wtf("EX", e)
                throw e
            } finally {
                listBlock.remove(block)
                if (listBlock.isEmpty())
                    withContext(Dispatchers.Main) {
                        hideLoading()
                    }
            }

            return@launch
        }
    }
}