package com.kotiln.permissionlibrary

import android.content.Context
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager

/**
 * Created by fulixin on 2017/8/9.
 */
class PermissionUtil {
    val TAG: String = "permissionutil"
    lateinit var fragment: PermissionFragment

    constructor() {}

    constructor(@NonNull context: Context) {
        fragment = getRxPermissionsFragment(context as FragmentActivity)
    }

    private fun getRxPermissionsFragment(context: FragmentActivity): PermissionFragment {
        var fragment = context.supportFragmentManager.findFragmentByTag(TAG)
        if (fragment == null) {
            fragment = PermissionFragment()
            var fragmentManager: FragmentManager = context.supportFragmentManager
            fragmentManager.beginTransaction().add(fragment, TAG).commit()
            fragmentManager.executePendingTransactions()
        }
        return fragment as PermissionFragment
    }

    public fun requestPermissions(permissions: Array<out String>, listener: PermissionListener) {
        fragment.listener = listener
        fragment.requestPermissions(permissions)
    }
}
