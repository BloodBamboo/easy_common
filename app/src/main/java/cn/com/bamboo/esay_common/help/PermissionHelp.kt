package cn.com.bamboo.esay_common.help

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import org.jetbrains.anko.toast

object Permission4MultipleHelp {
    val TGA = "PermissionFragment"
    var success: (() -> Unit)? = null
    var fail: ((List<String>?) -> Unit)? = null

    fun request(activity: FragmentActivity, permissions: Array<String>): Permission4MultipleHelp {
        val fragment = getFragment(activity)
        fragment.permission(permissions)
        success = {
            activity.toast("权限通过")
        }
        fail = {
            activity.toast("请允许同意全部权限")
        }
        return this
    }

    fun request(activity: FragmentActivity, permission: String):Permission4MultipleHelp  {
        return this.request(activity, arrayOf(permission))
    }

    private fun getFragment(activity: FragmentActivity): PermissionFragment {
        var temp: Fragment?
        val fragmentManger = activity.supportFragmentManager
        temp = fragmentManger.findFragmentByTag(TGA)
        if (temp == null) {
            temp = PermissionFragment()
            fragmentManger.beginTransaction().add(temp, TGA)
                .commitNow()
            return temp
        } else {
            return temp as PermissionFragment
        }
    }

    fun onPermission(
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        var result = true
        var permissionList = mutableListOf<String>()
        if (grantResults.isEmpty()) {
            fail?.run {
                this(null)
            }
            return
        }

        for (i in grantResults.indices) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                result = false
                permissionList.add(permissions[i])
            }
        }
        if (result) {
            success?.run {
                this()
            }
        } else {
            fail?.run {
                this(permissionList)
            }
        }
    }
}