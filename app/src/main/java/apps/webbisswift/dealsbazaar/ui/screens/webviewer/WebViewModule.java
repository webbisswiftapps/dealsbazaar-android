package apps.webbisswift.dealsbazaar.ui.screens.webviewer;

import dagger.Module;
import dagger.Provides;

/**
 * Created by biswas on 28/03/2017.
 */

@Module
public class WebViewModule {

    @Provides
    public WebViewActivityMVPContract.Presenter providePresenter(){
        return new WebViewPresenter();
    }


}
