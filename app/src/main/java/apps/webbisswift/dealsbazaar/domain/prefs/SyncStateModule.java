package apps.webbisswift.dealsbazaar.domain.prefs;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by biswas on 28/03/2017.
 */

@Module
public class SyncStateModule {

    @Singleton
    @Provides
    public SyncState provideSyncState(Context context){
        return new SyncState(context);
    }



}
