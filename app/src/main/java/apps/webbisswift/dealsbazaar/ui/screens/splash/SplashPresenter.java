package apps.webbisswift.dealsbazaar.ui.screens.splash;

import android.util.Log;

import apps.webbisswift.dealsbazaar.base.BaseView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by biswas on 28/03/2017.
 */

public class SplashPresenter implements SplashMVPContract.Presenter {

    SplashMVPContract.Model model;
    SplashMVPContract.View view;

    Disposable storeSubscription;

    public SplashPresenter(SplashMVPContract.Model model) {
        this.model = model;
    }



    @Override
    public void updateCaches() {
        storeSubscription =  model.updateStores().subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<Boolean>(){

                                @Override
                                public void onNext(Boolean value) {
                                    Log.d("SplashPresenter","Caches refreshed ");
                                }

                                @Override
                                public void onError(Throwable e) {

                                    Log.d("SplashPresenter","Error occured while updating cache. "+e.getMessage());
                                    if(view!=null)
                                        view.showMain();

                                }

                                @Override
                                public void onComplete() {
                                    if(view!=null)
                                        view.showMain();
                                }
                            });
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        if(!storeSubscription.isDisposed())
                storeSubscription.dispose();

        this.view = null;
        this.model = null;

    }

    @Override
    public void attachView(BaseView view) {
        this.view = (SplashMVPContract.View) view;
    }
}
