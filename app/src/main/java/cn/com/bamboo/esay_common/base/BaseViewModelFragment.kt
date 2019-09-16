package cn.com.edu.hnzikao.kotlin.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.jetbrains.anko.toast
import java.lang.reflect.ParameterizedType
abstract class BaseViewModelFragment<V : ViewDataBinding, VM : BaseViewModel> : BaseKotlinFragment() {
    protected lateinit var binding: V
    protected lateinit var viewModel: VM
    private var viewModelId: Int = 0

    protected var createViewModelOfActivity = false

    /**
     * 页面布局
     * @return
     */
    protected abstract fun initContentView(): Int

    /**
     * 初始化ViewModel的id
     * @return BR的id
     */
    protected abstract fun initVariableId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            initContentView(),
            container,
            false
        )
        viewModelId = initVariableId()
        val modelClass: Class<ViewModel>
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            modelClass = type.actualTypeArguments[1] as Class<ViewModel>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            modelClass = BaseViewModel::class.java as Class<ViewModel>
        }
        if (createViewModelOfActivity) {
            viewModel = createViewModel<ViewModel>(activity, modelClass) as VM
        } else {
            viewModel = createViewModel<ViewModel>(this, modelClass) as VM
        }

        binding.setVariable(viewModelId, viewModel)
        //让ViewModel拥有View的生命周期感应
        lifecycle.addObserver(viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //私有的ViewModel与View的契约事件回调逻辑
        registerUIChangeLiveDataCallBack()
        //页面数据初始化方法
        initData()
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    private fun <T : ViewModel> createViewModel(fragment: Fragment, cls: Class<T>): T {
        return ViewModelProviders.of(fragment).get(cls)
    }

    private fun <T : ViewModel> createViewModel(activity: FragmentActivity?, cls: Class<T>): T {
        return ViewModelProviders.of(activity!!).get(cls)
    }

    private fun registerUIChangeLiveDataCallBack() {
        viewModel?.message.observe(this, Observer {
            if (it?.get(ParameterField.RECEIVER_CLASS) != null
                && this@BaseViewModelFragment.javaClass.canonicalName != it?.get(ParameterField.RECEIVER_CLASS)
            ) {
                return@Observer
            }
            it?.get(ParameterField.MESSAGE)?.run { activity?.toast(this) }
        })
        viewModel?.startActivity.observe(this, Observer {
            val clz = it?.get(ParameterField.CLASS) as Class<*>
            val bundle = it?.get(ParameterField.BUNDLE) as Bundle?
            val intent = Intent(activity, clz)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            startActivity(intent)
        })
        viewModel?.finish.observe(this, Observer {
            if (it != null && this@BaseViewModelFragment.javaClass.canonicalName == it) {
                return@Observer
            }
            activity?.finish()
        })
    }

    protected open fun initData() {}
}