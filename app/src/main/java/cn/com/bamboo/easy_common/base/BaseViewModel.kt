package cn.com.edu.hnzikao.kotlin.base

import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.util.ArrayMap
import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

open class BaseViewModel(application: Application) : AndroidViewModel(application),
    LifecycleObserver {
    protected val subscriptions: CompositeDisposable = CompositeDisposable()

    var contextWeakReference: WeakReference<Context>? = null
    var startActivity = MutableLiveData<Map<String, Any>>()

    val message = MutableLiveData<Map<String, String>>()
    val finish = MutableLiveData<String>()

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected open fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onDestroy() {
        contextWeakReference?.clear()
        contextWeakReference = null
        subscriptions.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected open fun onStop() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onPause() {

    }

    /**
     * 发送消息
     * 如果viewModel没有公用可以不传接收类名
     * @param txt 消息内容
     * @param className 接收类名
     */
    @JvmOverloads
    protected fun setMessage(txt: String, className: String? = null) {
        val map = ArrayMap<String, String>()
        map[ParameterField.MESSAGE] = txt
        map[ParameterField.RECEIVER_CLASS] = className
        message.value = map
    }

    /**
     * 结束activity
     * 如果viewModel没有公用可以不传接收类名
     * @param className 接收类名
     */
    @JvmOverloads
    protected fun setFinish(className: String? = null) {
        finish.value = className
    }

    fun onError(
        sendMessage: (message: String) -> Unit,
        message: String = "请求失败",
        callback: () -> Unit = {}
    ): (Throwable) -> Unit {
        return { throwable ->
            if (throwable !is IllegalStateException) {
                throwable.printStackTrace()
            }
            if (!TextUtils.isEmpty(throwable.message)) {
                sendMessage(throwable.message!!)
            } else {
                sendMessage(message)
            }
            callback()
        }
    }
}