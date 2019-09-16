package cn.com.bamboo.esay_common

import android.os.Bundle
import cn.com.edu.hnzikao.kotlin.base.BaseKotlinActivity

class MainActivity : BaseKotlinActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitleAndBackspace("首页")
    }
}
