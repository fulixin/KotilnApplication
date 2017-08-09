package com.kotiln.permissionlibrary

/**
 * Created by fulixin on 2017/8/9.
 */
interface PermissionListener {
    fun onGranted()
    fun onDenied(deniedPermission: List<String>)
    fun onShouldShowRationale(deniedPermission: List<String>)
}