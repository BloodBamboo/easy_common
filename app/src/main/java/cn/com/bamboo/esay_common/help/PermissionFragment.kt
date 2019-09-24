package cn.com.bamboo.esay_common.help

import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionFragment : Fragment() {
    private val mRequest = 111//生产请求code


    fun permission(permissions: Array<String>) {
        if (check(permissions)) {
            Permission4MultipleHelp.success?.run {
                this()
            }
        } else {
           requestPermissions(permissions, mRequest)
        }
    }

    private fun check(permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        } else {
            var result = true
            for (p in permissions) {
                if (ContextCompat.checkSelfPermission(context!!, p)
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
            return result
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == mRequest) {
            Permission4MultipleHelp.onPermission(permissions, grantResults)
        }
    }
}