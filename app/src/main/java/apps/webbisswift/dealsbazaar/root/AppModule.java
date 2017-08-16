package apps.webbisswift.dealsbazaar.root;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by biswas on 25/03/2017.
 */

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    public Context provideContext(){
        return application;
    }

}
