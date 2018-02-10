package ch.deletescape.lawnchair;

import android.app.Application;

import ch.deletescape.lawnchair.preferences.PreferenceImpl;
import ch.deletescape.lawnchair.preferences.PreferenceProvider;
import com.rberidon.KeyoneShortcuts;

public class App extends Application {

     @Override
    public void onCreate() {
        super.onCreate();

        PreferenceProvider.INSTANCE.init(new PreferenceImpl(this));
        KeyoneShortcuts.INSTANCE.init(this);
    }
}
