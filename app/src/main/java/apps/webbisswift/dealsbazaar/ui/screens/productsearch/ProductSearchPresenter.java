package apps.webbisswift.dealsbazaar.ui.screens.productsearch;

import android.util.Log;

import java.util.List;

import apps.webbisswift.dealsbazaar.base.BaseView;
import apps.webbisswift.dealsbazaar.domain.database.SearchHistory;
import apps.webbisswift.dealsbazaar.ui.viewmodels.DealSectionVM;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by biswas on 01/04/2017.
 */

public class ProductSearchPresenter implements ProductSearchMVPContract.Presenter {

    ProductSearchMVPContract.View view;
    ProductSearchMVPContract.Model model;

    public ProductSearchPresenter(ProductSearchMVPContract.Model model) {
        this.model = model;
    }

    Disposable productSubscription;
    Disposable searchHistorySubscription;


    @Override
    public void requestProducts(String query) {
        productSubscription = this.model.loadProducts(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<DealSectionVM>(){

                    int onNextCount = 0;

                    @Override
                    public void onNext(DealSectionVM section) {

                        onNextCount++;

                        if(view!=null) {
                            view.addSearchResultSection(section);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                        if(view!=null) {
                            if (onNextCount <= 0) {
                                    view.productsLoadError("Oops! We could not find any products for you at this time.");
                            } else {
                                    view.hideProductsLoading();
                                    view.fillAds();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if(view!=null) {
                            if(onNextCount > 0) {
                                view.hideProductsLoading();
                                view.fillAds();
                            }else{
                                view.productsLoadError("Sorry, we could not find any products matching your query. \n Please check the keyword and try again.");
                            }
                        }
                    }
                });
    }

    @Override
    public void cancelPreviousRequest() {
        if(this.productSubscription!= null && !productSubscription.isDisposed()){
            this.productSubscription.dispose();
        }
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

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        if(productSubscription!=null && !productSubscription.isDisposed())
            productSubscription.dispose();

        if(searchHistorySubscription != null){
            if(!searchHistorySubscription.isDisposed())
                searchHistorySubscription.dispose();
        }

        this.view = null;
        this.model = null;

    }

    @Override
    public void attachView(BaseView view) {
        this.view = (ProductSearchMVPContract.View) view;
    }
}
