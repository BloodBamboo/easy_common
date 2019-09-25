package cn.com.bamboo.esay_common

import android.Manifest
import android.content.Intent
import android.os.Bundle
import cn.com.bamboo.esay_common.help.Permission4MultipleHelp
import cn.com.edu.hnzikao.kotlin.base.BaseKotlinActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseKotlinActivity() {

    var i = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
