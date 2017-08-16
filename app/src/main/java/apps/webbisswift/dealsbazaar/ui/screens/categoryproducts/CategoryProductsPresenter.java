package apps.webbisswift.dealsbazaar.ui.screens.categoryproducts;

import android.support.annotation.Nullable;

import apps.webbisswift.dealsbazaar.base.BaseView;
import apps.webbisswift.dealsbazaar.ui.viewmodels.DealSectionVM;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by biswas on 15/05/2017.
 */

public class CategoryProductsPresenter implements CategoryProductsMVPContract.Presenter {


    CategoryProductsMVPContract.Model model;
    @Nullable
    CategoryProductsMVPContract.View view;

    public CategoryProductsPresenter(CategoryProductsMVPContract.Model model){
        this.model = model;
    }

    Disposable productSubscription;


    @Override
    public void requestProducts(String main, String sub) {
        productSubscription = this.model.loadProducts(main, sub)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<DealSectionVM>(){

                    int onNextCount = 0;

                    @Override
                    public void onNext(DealSectionVM section) {

                        onNextCount++;

                        if(view!=null) {
                            view.addProductsSection(section);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                        if(view!=null) {
                            if (onNextCount <= 0)
                                view.productsLoadError("Oops! We could not find any products for you at this time. :( ");
                            else {
                                view.hideProductsLoading();
                                view.fillAds();
                            }


                        }
                    }

                    @Override
                    public void onComplete() {
                        if(view!=null) {
                            view.hideProductsLoading();
                            view.fillAds();
                        }
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
        this.view = null;
        this.model = null;
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (CategoryProductsMVPContract.View) view;
    }
}
