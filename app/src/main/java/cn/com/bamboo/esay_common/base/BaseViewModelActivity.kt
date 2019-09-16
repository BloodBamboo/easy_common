package cn.com.edu.hnzikao.kotlin.base

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import org.jetbrains.anko.toast
import java.lang.reflect.ParameterizedType

abstract class BaseViewModelActivity<V : ViewDataBinding, VM : BaseViewModel> : BaseKotlinActivity() {
    protected lateinit var binding: V
    protected lateinit var viewModel: VM

    private var viewModelId: Int = 0

    /**
     * 页面布局
     * @return
     */
    abstract fun initContentView(): Int

    /**
     * 初始化ViewModel的id
     * @return BR的id
     */
    abstract fun initVariableId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBindViewModel(savedInstanceState)
        registerUIChangeLiveDataCallBack()
    }

    private fun initBindViewModel(savedInstanceState: Bundle?) {
        viewModelId = initVariableId()

        binding = DataBindingUtil.setContentView(this, initContentView())
        val modelClass: Class<ViewModel>
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            modelClass = type.actualTypeArguments[1] as Class<ViewModel>
        } else {
            modelClass = BaseViewModel::class.java as Class<ViewModel>
        }
        if (viewModelId == 0) {
            return
        }
        viewModel = createViewModel<ViewModel>(this, modelClass) as VM

        //关联ViewModel
        binding.setVariable(viewModelId, viewModel)
        //让ViewModel拥有View的生命周期感应
        lifecycle.addObserver(viewModel)
    }

    private fun registerUIChangeLiveDataCallBack() {
        viewModel?.message.observe(this, Observer {
            if (it?.get(ParameterField.RECEIVER_CLASS) != null
                && this@BaseViewModelActivity.javaClass.canonicalName != it?.get(ParameterField.RECEIVER_CLASS)
            ) {
                return@Observer
            }
            it?.get(ParameterField.MESSAGE)?.run { toast(this) }
        })
        viewModel.startActivity.observe(this, Observer {
            val clz = it?.get(ParameterField.CLASS) as Class<*>
            val bundle = it?.get(ParameterField.BUNDLE) as Bundle?
            val intent = Intent(this, clz)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            startActivity(intent)
        })
        viewModel.finish.observe(this, Observer {
            if (it != null && this@BaseViewModelActivity.javaClass.canonicalName == it) {
                return@Observer
            }
            finish()
        })
    }

    /**
     * 创建ViewModel
     */
    private fun <T : ViewModel> createViewModel(activity: FragmentActivity, cls: Class<T>): T {
        return ViewModelProviders.of(activity).get(cls)
    }

    override fun onDestroy() {
        super.onDestroy()
        //解除ViewModel生命周期感应
        lifecycle.removeObserver(viewModel)
    }


}
