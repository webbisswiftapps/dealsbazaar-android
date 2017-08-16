package apps.webbisswift.dealsbazaar.ui.screens.splash;

import apps.webbisswift.dealsbazaar.base.BasePresenter;
import apps.webbisswift.dealsbazaar.base.BaseView;
import io.reactivex.Observable;

/**
 * Created by biswas on 28/03/2017.
 */

public interface SplashMVPContract {

    interface View extends BaseView {

        /* Slide Loading & Setting methods*/
        void showMain();
    }

    interface Presenter extends BasePresenter {
        void updateCaches();
    }

    interface Model{
        Observable<Boolean> updateStores();
    }

}
