package cn.com.bamboo.easy_common

import android.Manifest
import android.content.Intent
import android.os.Bundle
import cn.com.bamboo.easy_common.help.Permission4MultipleHelp
import cn.com.edu.hnzikao.kotlin.base.BaseKotlinActivity
import kotlinx.android.synthetic.main.activity_common.*
import org.jetbrains.anko.toast

class MainActivity : BaseKotlinActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        setTitleAndBackspace("首页")
        text.setOnClickListener {
            startCamera()
                        startActivity(Intent(this, SecondActivity::class.java))
        }
        test()
        supportFragmentManager.beginTransaction().add(R.id.fragment, TestFragment())
            .commit()
    }

    fun startCamera() {
        r()
    }

    fun r() {
        Permission4MultipleHelp.request(this, arrayOf(Manifest.permission.CAMERA), fail = {
            r()
        }, success = {
            toast("开启摄像头")
        })
    }

    fun test() {
        Permission4MultipleHelp
            .request(
                this,
                arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
                ),
                shouldShowRequest = true
        )
    }
}
