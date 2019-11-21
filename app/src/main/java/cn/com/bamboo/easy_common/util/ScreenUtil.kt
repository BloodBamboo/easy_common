package cn.com.bamboo.easy_common.util

import android.content.Context
import android.util.DisplayMetrics

import java.lang.reflect.Field

object ScreenUtil {
    /**
     * 根据手机分辨率将dp转为px单位
     */
    fun dip2px(mContext: Context, dpValue: Float): Int {
        val scale = mContext.resources
            .displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(mContext: Context, pxValue: Float): Int {
        val scale = mContext.resources
            .displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 屏幕宽高
     *
     * @param mContext 上下文
     * @return
     */
    private fun getScreenSize(mContext: Context): IntArray {
        val dm = mContext
            .resources.displayMetrics
        val screenWidth = dm.widthPixels
        val screenHeight = dm.heightPixels

        return intArrayOf(screenWidth, screenHeight)
    }

    /**
     * 获取状态栏高度
     *
     * @param mContext 上下文
     * @return
     */
    fun getStatusBarHeight(mContext: Context): Int {
        var c: Class<*>? = null
        var obj: Any? = null
        var field: Field? = null
        var x = 0
        var statusBarHeight = 0
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            obj = c!!.newInstance()
            field = c.getField("status_bar_height")
            x = Integer.parseInt(field!!.get(obj).toString())
            statusBarHeight = mContext.resources.getDimensionPixelSize(x)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

        return statusBarHeight
    }

    /**
     * 获取手机屏幕的宽度
     *
     * @param mContext 上下文
     * @return
     */
    fun getScreenWidth(mContext: Context): Int {
        val screen = getScreenSize(mContext)
        return screen[0]
    }

    /**
     * 获取手机屏幕的高度
     *
     * @param mContext 上下文
     * @return
     */
    fun getScreenHeight(mContext: Context): Int {
        val screen = getScreenSize(mContext)
        return screen[1]
    }
}
