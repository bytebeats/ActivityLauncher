package me.bytebeats.launcher

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ReportFragment

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/4/3 20:19
 * @Version 1.0
 * @Description TO-DO
 */

class ActivityLauncher private constructor(private val activity: FragmentActivity) {

    private val mRouterFragment by lazy { findRouterFragment() }

    companion object {
        private const val TAG = "ActivityLauncher"
        fun with(activity: FragmentActivity): ActivityLauncher {
            return ActivityLauncher(activity)
        }
    }

    private fun findRouterFragment(): RouterFragment {
        val supportFragmentManager = activity.supportFragmentManager
        var routerFragment = supportFragmentManager.findFragmentByTag(TAG) as RouterFragment?
        if (routerFragment == null) {
            routerFragment = RouterFragment.newInstance()
            supportFragmentManager.beginTransaction().add(routerFragment, TAG).commitAllowingStateLoss()
            supportFragmentManager.executePendingTransactions()
        }
        return routerFragment
    }

    fun startActivityForResult(intent: Intent, callback: RouterFragment.ActivityResultCallback) {
        mRouterFragment.startActivityForResult(intent, callback)
    }

    fun startActivityForResult(clazz: Class<FragmentActivity>, callback: RouterFragment.ActivityResultCallback) {
        mRouterFragment.startActivityForResult(Intent(activity, clazz), callback)
    }

    fun requestPermissions(
        permissions: Array<out String>, callback: RouterFragment.RequestPermissionCallback?
    ) {
        mRouterFragment.requestPermissions(permissions, callback)
    }

}