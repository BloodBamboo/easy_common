package cn.com.bamboo.esay_common.help

data class PermissionsInfo(
    var name: String,
    var shouldShowRequestPermissionRationale: Boolean,
    var granted: Boolean
)