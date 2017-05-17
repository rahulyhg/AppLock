package anand.android.applockclone.dagger.components;


import javax.inject.Singleton;

import anand.android.applockclone.dagger.modules.AppModule;
import anand.android.applockclone.ui.activities.InitialSetupScreen;
import anand.android.applockclone.ui.activities.MainActivity;
import dagger.Component;


@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(InitialSetupScreen activity);
}
