package cn.com.edu.hnzikao.kotlin.base

import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import cn.com.bamboo.easy_common.R
import me.yokeyword.fragmentation.SupportFragment


open class BaseKotlinFragment : SupportFragment() {
    protected var toolbar: Toolbar? = null

    /**
     * 不能把toolbar设置到actionbar上,不然返回失败得使用actionbar的返回事件
     *
     * @param title
     * @param iconId
     */
    protected fun setTitleAndBackspace(
        title: String,
        iconId: Int = R.mipmap.ic_arrow_back_white,
        listener: (() -> Unit)? = null
    ) {
        this.toolbar = view?.findViewById(R.id.toolbar)
        if (toolbar != null) {
            var titleTextView: TextView? = toolbar?.findViewById(R.id.toolbar_title)
            if (titleTextView == null) {
                toolbar?.setTitle(title)
            } else {
                titleTextView.setText(title)
            }

//            toolbar?.setContentInsetStartWithNavigation(0)
            toolbar?.setNavigationIcon(iconId)
            toolbar?.setNavigationOnClickListener {
                if (listener != null) {
                    listener()
                } else {
                    activity?.finish()
                }
            }
        }
    }
}