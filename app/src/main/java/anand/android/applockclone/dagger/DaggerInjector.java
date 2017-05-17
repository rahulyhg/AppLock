package anand.android.applockclone.dagger;


import anand.android.applockclone.dagger.components.AppComponent;
import anand.android.applockclone.dagger.components.DaggerAppComponent;
import anand.android.applockclone.dagger.modules.AppModule;

public class DaggerInjector {
    private static AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();

    public static AppComponent get() {
        return appComponent;
    }
}
