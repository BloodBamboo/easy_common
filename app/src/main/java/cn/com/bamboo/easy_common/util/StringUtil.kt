package cn.com.bamboo.easy_common.util

import android.content.Context
import cn.com.bamboo.easy_common.R

class StringUtil {
    companion object {
        /**
         * 格式化播放时间
         */
        fun timestampToMSS(context: Context, totalSeconds: Long?): String {
            if (totalSeconds == null) {
                return context.getString(R.string.duration_unknown)
            }
            val temp = totalSeconds / 1000
            val minutes = temp / 60
            val remainingSeconds = temp - (minutes * 60)
            return if (totalSeconds < 0) context.getString(R.string.duration_unknown)
            else context.getString(R.string.duration_format).format(
                minutes,
                remainingSeconds
            )
        }
    }
}