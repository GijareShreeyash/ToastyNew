package com.reapmind.toasty

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.reapmind.toasty.utils.SocketHandler

class ToastyApplication: Application() {

    private var activityCounter: Int = 0

    override fun onCreate() {
        super.onCreate()


        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, p1: Bundle?) {
                if (activityCounter == 0) {
                    SocketHandler.establishConnection();
                }
                activityCounter++;
            }

            override fun onActivityStarted(activity: Activity) {
                Log.i("APPLICATION_LIFECYCLE", "onActivityStarted${activity.localClassName}")
                Log.i("APPLICATION_LIFECYCLE", "onActivityStarted${activity.localClassName}")
            }

            override fun onActivityResumed(activity: Activity) {
                Log.i("APPLICATION_LIFECYCLE", "onActivityResumed${activity.callingActivity.toString()}")
            }

            override fun onActivityPaused(activity: Activity) {
                Log.i("APPLICATION_LIFECYCLE", "onActivityPaused${activity.callingActivity.toString()}")
            }

            override fun onActivityStopped(activity: Activity) {
                activityCounter--
                if (activityCounter == 0) {
                    SocketHandler.socketDisconnect();
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
                Log.i("APPLICATION_LIFECYCLE", "onActivitySaveInstanceState")
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.i("APPLICATION_LIFECYCLE", "onActivityDestroyed")
            }
        })

    }

}