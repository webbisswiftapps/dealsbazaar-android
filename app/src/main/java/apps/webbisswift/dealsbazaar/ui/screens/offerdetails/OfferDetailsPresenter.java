package apps.webbisswift.dealsbazaar.ui.screens.offerdetails;



import java.io.IOException;
import java.util.ArrayList;

import apps.webbisswift.dealsbazaar.base.BaseView;
import apps.webbisswift.dealsbazaar.domain.net.exceptions.NoProductsFoundInOfferException;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by biswas on 29/03/2017.
 */

public class OfferDetailsPresenter implements OfferDetailsMVPContract.Presenter {

    OfferDetailsMVPContract.View view;
    OfferDetailsMVPContract.Model model;

    public OfferDetailsPresenter(OfferDetailsMVPContract.Model model) {
        this.model = model;
    }

    Disposable productSubscription;
    boolean initialProductsAddedToView = false;
    ArrayList<ProductVM> initialProducts;


    @Override
    public void setInitialProducts(ArrayList<ProductVM> array) {
        this. initialProducts = array;
    }


    @Override
    public void requestProducts(final String url, String storePath) {
        productSubscription = this.model.loadProducts(url, storePath)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableObserver<ProductVM>(){
                                    @Override
                                    public void onNext(ProductVM product) {
                                        if(view!=null) {

                                                view.addProduct(product);

                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        if(view!=null) {

                                            if(e instanceof NoProductsFoundInOfferException){
                                                if(initialProducts==null || initialProducts.size() <= 0) {
                                                    view.loadInWebView(url);
                                                }else{
                                                    view.hideProductsLoading();
                                                }
                                            }else if(e instanceof IOException){
                                                view.productsLoadError("Connectivity Error.");
                                            }else view.productsLoadError(e.getMessage());
                                        }
                                    }

                                    @Override
                                    public void onComplete() {
                                        if(view!=null)
                                            view.hideProductsLoading();
                                    }
                                });
    }

    @Override
    public void requestMore(){
        productSubscription = this.model.loadNextPage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ProductVM>(){
                    @Override
                    public void onNext(ProductVM product) {
                        if(view!=null) {
                            view.addProduct(product);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(view!=null) {

                           if(e instanceof IOException){
                                view.productsLoadError("Connectivity Error. Please try again in a while.");
                            }else {
                                view.productsLoadError(e.getMessage());
                            }
                            view.stopLoadingMore();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if(view!=null)
                            view.hideProductsLoading();
                    }
                });
    }

    @Override
    public boolean canRequestMore() {
        return model.hasNextPage();
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

        this.view = null;
        this.model = null;
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (OfferDetailsMVPContract.View) view;
        if(this.initialProducts!=null && this.initialProducts.size() > 0 && !this.initialProductsAddedToView){
                for(ProductVM pro : this.initialProducts){
                    this.view.addProduct(pro);
                }
            initialProductsAddedToView = true;
        }

    }
}
