package apps.webbisswift.dealsbazaar.ui.screens.offerdetails;


import java.util.ArrayList;

import apps.webbisswift.dealsbazaar.base.BasePresenter;
import apps.webbisswift.dealsbazaar.base.BaseView;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import io.reactivex.Observable;

/**
 * Created by biswas on 29/03/2017.
 */

public interface OfferDetailsMVPContract {

    interface View extends BaseView {


        /* Deals Loading & Setting methods */
        void showProductsLoading(boolean more);
        void hideProductsLoading();
        void addProduct(ProductVM dealsection);
        void productsLoadError(String error);
        void loadInWebView(String url);
        void stopLoadingMore();

        /* Ad Loading */
        void showTopBanner();
        void hideTopBanner();
    }

    interface Presenter extends BasePresenter {
        void setInitialProducts(ArrayList<ProductVM> array);
        void requestProducts(String url, String storePath);
        boolean canRequestMore();
        void requestMore();
    }

    interface Model{
        Observable<ProductVM> loadProducts(String url, String storePath);
        boolean hasNextPage();
        Observable<ProductVM> loadNextPage();
    }


}
