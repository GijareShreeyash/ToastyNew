package com.reapmind.toasty

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.reapmind.toasty.utils.SocketHandler
import org.json.JSONException
import org.json.JSONObject
import java.util.Calendar

class ToastyApplication : Application() {

    private var activityCounter: Int = 0
    private var startTime: Long = 0L

    private var listOfStartTime: MutableList<Long> = mutableListOf()

    override fun onCreate() {
        super.onCreate()

        if (activityCounter == 0) {
            SocketHandler.establishConnection();
        }


        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, p1: Bundle?) {
                activityCounter++;
                startTime = 0
                startTime = System.currentTimeMillis()
                listOfStartTime.add(startTime)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {
                Log.i(
                    "APPLICATION_LIFECYCLE",
                    "onActivityResumed${activity.callingActivity.toString()}"
                )
            }

            override fun onActivityPaused(activity: Activity) {

                val jsonObject = JSONObject()
                try {
                    jsonObject.put("data", activity.localClassName)
                    jsonObject.put("message", activity.localClassName)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                SocketHandler.emitTrack(
                    activity.localClassName,
                    activity.localClassName,
                    SocketHandler.findDifference(
                        listOfStartTime[0],
                        System.currentTimeMillis()
                    ).toInt(),
                    startTime,
                    System.currentTimeMillis(),
                    jsonObject
                )
                listOfStartTime.removeAt(0)

                activityCounter--
                if (activityCounter == 0) {
                    SocketHandler.socketDisconnect();
                }
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
                Log.i("APPLICATION_LIFECYCLE", "onActivitySaveInstanceState")
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.i("APPLICATION_LIFECYCLE", "onActivityStarted${activity.localClassName}")
                Log.i("APPLICATION_LIFECYCLE", "onActivityStarted${activity.localClassName}")


            }
        })

    }



    override fun onTerminate() {
        super.onTerminate()
        /*activityCounter--
        if (activityCounter == 0) {
            SocketHandler.socketDisconnect();
        }*/
    }


}