package cn.com.bamboo.esay_common

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.com.bamboo.esay_common.help.Permission4MultipleHelp
import kotlinx.android.synthetic.main.fragment.*

class TestFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_fragment.setOnClickListener{
            activity?.let { it1 -> Permission4MultipleHelp.request(it1, Manifest.permission.CAMERA) }
        }
    }
}