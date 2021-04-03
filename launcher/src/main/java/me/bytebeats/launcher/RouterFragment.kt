package me.bytebeats.launcher

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
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

internal class RouterFragment private constructor() : Fragment() {
    private val mCallbacks = SparseArray<ActivityLauncher.Callback>()
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

    fun startActivityForResult(intent: Intent, callback: ActivityLauncher.Callback) {
        val requestCode = makeRequestCode()
        mCallbacks[requestCode] = callback
        startActivityForResult(intent, requestCode)
    }

    private fun makeRequestCode(): Int {
        var requestCode = 0
        var retry = 0
        do {
            requestCode = mRequestCodeGenerator.nextInt(0xffff)
            retry += 1
        } while (mCallbacks.indexOfKey(requestCode) >= 0 && retry < 10)
        return requestCode
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbacks[requestCode]?.let {
            it.onActivityResult(requestCode, resultCode, data)
            mCallbacks.remove(requestCode)
        }
    }

}