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

        SocketHandler.establishConnection()

    }

}