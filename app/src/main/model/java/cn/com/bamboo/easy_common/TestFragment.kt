package cn.com.bamboo.easy_common.cn.com.bamboo.easy_common

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.com.bamboo.easy_common.R
import cn.com.bamboo.easy_common.help.Permission4MultipleHelp
import kotlinx.android.synthetic.main.fragment.*
import org.jetbrains.anko.toast

class TestFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_fragment.setOnClickListener{
            activity?.let { it ->
                Permission4MultipleHelp.request(
                    it,
                    arrayOf(Manifest.permission.CAMERA),
                    success = {
                        it.toast("fragment 权限请求成功")
                    })
            }
        }
    }
}