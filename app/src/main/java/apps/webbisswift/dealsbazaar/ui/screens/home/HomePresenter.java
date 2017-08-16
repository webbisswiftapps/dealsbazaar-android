package apps.webbisswift.dealsbazaar.ui.screens.home;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import apps.webbisswift.dealsbazaar.base.BaseView;
import apps.webbisswift.dealsbazaar.domain.database.SearchHistory;
import apps.webbisswift.dealsbazaar.ui.viewmodels.DealSectionVM;
import apps.webbisswift.dealsbazaar.ui.viewmodels.SlideVM;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by biswas on 25/03/2017.
 */

public class HomePresenter implements HomeMVPContract.Presenter {

    @Nullable
    private HomeMVPContract.View view;
    private HomeMVPContract.Model model;

    private Disposable slideSubscription;
    private Disposable dealsSubscription;
    private Disposable storeSubscription;
    private Disposable searchHistorySubscription;
    private Disposable recommendedProductSubscription;

    private boolean recommendedAdded;


    public HomePresenter(HomeMVPContract.Model model) {
        this.model = model;
    }




    @Override
    public void requestSlides() {
        if(view!=null){
            view.showSlidesLoading();
        }
        slideSubscription = model.loadSlides()
                            .take(8)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<SlideVM>(){

                                @Override
                                public void onNext(SlideVM value) {
                                    if(view!=null){
                                        Log.d("SlideX",value.getSlideURL());
                                        view.addSlide(value);
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    if(view!=null){
                                        view.showSlideError(e.getMessage());
                                    }
                                }

                                @Override
                                public void onComplete() {
                                    if(view!=null){
                                        view.hideSlidesLoading();
                                    }
                                }

                            });

    }

    @Override
    public void requestDeals() {
        if(view!=null)
            view.showDealsLoading();



        dealsSubscription = model.loadDeals()
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<DealSectionVM>(){

                                int onNextCount = 0;


                                @Override
                                public void onNext(DealSectionVM section) {
                                    onNextCount ++;
                                    if(view!=null) {
                                        view.addDealSection(section);
                                    }

                                }

                                @Override
                                public void onError(Throwable e) {
                                     if(view!=null) {
                                             e.printStackTrace();
                                         if(onNextCount <= 0) {

                                                 view.dealLoadError("Oops! We could not find any products for you at this time.");

                                         }else

                                         view.hideDealsLoading();
                                         view.fillAdsInList();
                                     }
                                }

                                @Override
                                public void onComplete() {
                                    if(view!=null) {
                                        if(onNextCount > 0){
                                            view.fillAdsInList();
                                        }else{
                                            view.dealLoadError("Oops! We could not find any products for you at this time.");
                                        }

                                        view.hideDealsLoading();
                                    }
                                }
                            });

    }

    @Override
    public void requestRecommendedProducts(String query) {
        recommendedProductSubscription = this.model.loadRecommendedProducts(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<DealSectionVM>(){


                    @Override
                    public void onNext(DealSectionVM section) {
                        if(view!=null) {
                            view.addDealSection(section);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void requestSearchHistory() {
        searchHistorySubscription = model.loadSearchHistory().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<SearchHistory>>(){

                    @Override
                    public void onNext(List<SearchHistory> value) {
                        if(view!=null){
                            view.setSearchHistory(value);

                            if(!recommendedAdded) {
                                if (value.size() > 0) {
                                /* Search latest searched product */
                                    requestRecommendedProducts(value.get(0).getQuery());
                                    recommendedAdded = true;
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("HomePresenter","Search history not found.");
                    }

                    @Override
                    public void onComplete() {
                    }

                });
    }

    @Override
    public void insertSearchHistory(String query) {
        model.insertSearchHistory(query)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void resume() {
        /* Perform any thing you would normally do on an activity's onResume here */

    }

    @Override
    public void pause() {
        /* Perform any thing you would normally do on an activity's onPause here */
    }

    @Override
    public void destroy() {
        /* Perform any thing you would normally do on an activity's onDestroy here */

        if(storeSubscription!=null){
            if(!storeSubscription.isDisposed())
                storeSubscription.dispose();
        }

        if(slideSubscription!=null){
            if(!slideSubscription.isDisposed())
                slideSubscription.dispose();
        }

        if(dealsSubscription!=null){
            if(!dealsSubscription.isDisposed())
                dealsSubscription.dispose();
        }

        if(searchHistorySubscription != null){
            if(!searchHistorySubscription.isDisposed())
                searchHistorySubscription.dispose();
        }

        this.view = null;
        this.model = null;

    }

    @Override
    public void attachView(BaseView view) {
        this.view = (HomeMVPContract.View) view;
    }
}
