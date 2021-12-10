package me.bytebeats.launcher

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import androidx.core.app.ActivityCompat
import androidx.core.util.set
import androidx.fragment.app.Fragment
import kotlin.random.Random

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/4/3 20:21
 * @Version 1.0
 * @Description TO-DO
 */

class RouterFragment private constructor() : Fragment() {
    private val mActivityResultCallbacks = SparseArray<ActivityResultCallback>()
    private val mRequestPermissionCallbacks = SparseArray<RequestPermissionCallback>()
    private val mRequestCodeGenerator = Random(0)

    companion object {
        fun newInstance(): RouterFragment {
            return RouterFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun startActivityForResult(intent: Intent, callback: ActivityResultCallback) {
        val requestCode = makeRequestCode()
        mActivityResultCallbacks[requestCode] = callback
        startActivityForResult(intent, requestCode)
    }

    fun requestPermissions(
        permissions: Array<out String>,
        callback: RequestPermissionCallback?
    ) {
        val requestCode = makeRequestCode()
        mRequestPermissionCallbacks.put(requestCode, callback)
        ActivityCompat.requestPermissions(requireActivity(), permissions, requestCode)
    }

    private fun makeRequestCode(): Int {
        var requestCode = 0
        var retry = 0
        do {
            requestCode = mRequestCodeGenerator.nextInt(0xffff)
            retry += 1
        } while (mActivityResultCallbacks.indexOfKey(requestCode) >= 0 && retry < 10)
        return requestCode
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        mRequestPermissionCallbacks[requestCode]?.let {
            it.onRequestPermissionsResult(requestCode, permissions, grantResults)
            mRequestPermissionCallbacks.remove(requestCode)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mActivityResultCallbacks[requestCode]?.let {
            it.onActivityResult(resultCode, data)
            mActivityResultCallbacks.remove(requestCode)
        }
    }

    interface ActivityResultCallback {
        fun onActivityResult(resultCode: Int, data: Intent?)
    }

    interface RequestPermissionCallback {
        fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        )
    }
}