package anand.android.applockclone.utils

import android.app.Application
import com.google.android.gms.analytics.Tracker
import java.util.*

/**
 * Created by amitshekhar on 01/05/15.
 */
class AppLockApplication : Application() {

    internal var mTrackers = HashMap<TrackerName, Tracker>()

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    enum class TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER // Tracker used by all the apps from a company. eg:
        // roll-up tracking.
    }

    companion object {
        val PROPERTY_ID = "UA-62504955-1"
        /**
         * Get an instance of application
         * @return
         */
        @get:Synchronized var instance: AppLockApplication? = null
            private set
    }
}
