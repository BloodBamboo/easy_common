package cn.com.bamboo.easy_common.cn.com.bamboo.easy_common

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.com.bamboo.easy_common.R
import cn.com.bamboo.easy_common.help.Permission4MultipleHelp
import kotlinx.android.synthetic.main.activity_common.*

class SecondActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        text.setOnClickListener {
            Permission4MultipleHelp
                .request(
                    this, arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                    )
                )
        }
    }
}