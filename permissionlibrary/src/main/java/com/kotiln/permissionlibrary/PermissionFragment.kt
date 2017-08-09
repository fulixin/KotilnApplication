package com.kotiln.permissionlibrary

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import java.util.*

/**
 * Created by fulixin on 2017/8/9.
 */
class PermissionFragment : Fragment() {
    val PERMISSIONS_REQUEST_CODE = 1
    lateinit var listener: PermissionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
    }

    fun requestPermissions(permissions: Array<out String>) {
        var requestPermissionList: MutableList<String> = ArrayList()
        //找出所有未授权的
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionList.add(permission)
            }
        }
        if (requestPermissionList.size == 0) {
            //已经全部授权
            permissionAllGranted()
        } else {
            //申请授权
            requestPermissions(permissions, PERMISSIONS_REQUEST_CODE)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != PERMISSIONS_REQUEST_CODE) {
            return
        }
        if (grantResults!!.size > 0) {
            var deniedPermissionList: MutableList<String> = ArrayList()
            for (i in 0..grantResults!!.size-1) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissionList.add(permissions!![i])
                }
            }
            if (deniedPermissionList.size == 0) {
                //已全部授权
                permissionAllGranted()
            } else {
                //勾选对话框不在提醒的，返回false
                for (s in deniedPermissionList) {
                    var flag: Boolean = shouldShowRequestPermissionRationale(s)
                    if (!flag) {
                        //拒绝授权
                        permissionShouldShowRationale(deniedPermissionList)
                        return
                    }
                }
                //拒绝授权
                permissionHasDdnied(deniedPermissionList)
            }
        }
    }

    /**
     * 权限被拒绝
     */
    fun permissionHasDdnied(deniedPermissionList: List<String>) {
        if (listener != null) {
            listener.onDenied(deniedPermissionList)
        }
    }

    /**
     * 被拒绝
     * 勾选了不在询问的权限
     */
    fun permissionShouldShowRationale(deniedPermissionList: List<String>) {
        if (listener != null) {
            listener.onShouldShowRationale(deniedPermissionList)
        }
    }

    /**
     * 权限全部授权
     */
    private fun permissionAllGranted() {
        if (listener != null)
            listener.onGranted()
    }


}