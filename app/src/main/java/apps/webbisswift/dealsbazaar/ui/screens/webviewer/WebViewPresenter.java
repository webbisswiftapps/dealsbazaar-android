package apps.webbisswift.dealsbazaar.ui.screens.webviewer;

import apps.webbisswift.dealsbazaar.base.BaseView;

/**
 * Created by biswas on 28/03/2017.
 */

public class WebViewPresenter implements WebViewActivityMVPContract.Presenter {

    String currentURL;

    WebViewActivityMVPContract.View view;

    public WebViewPresenter() {
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.view = null;
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (WebViewActivityMVPContract.View) view;
    }

}
