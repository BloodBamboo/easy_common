package cn.com.bamboo.esay_common

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.com.bamboo.esay_common.help.Permission4MultipleHelp
import kotlinx.android.synthetic.main.activity_main.*

class SecondActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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