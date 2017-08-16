package apps.webbisswift.dealsbazaar.domain.prefs;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by biswas on 14/05/2017.
 */

@Module
public class ValidCategoryCacheModule {

    @Singleton
    @Provides
    public ValidCategoryCache provideValidCategoryCache(Context context){
        return new ValidCategoryCache(context);
    }



}