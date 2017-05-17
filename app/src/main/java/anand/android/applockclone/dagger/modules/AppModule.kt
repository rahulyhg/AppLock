package anand.android.applockclone.dagger.modules


import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideSharedPreference(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

}