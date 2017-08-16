package apps.webbisswift.dealsbazaar.ui.screens.categoryproducts;

import apps.webbisswift.dealsbazaar.base.BasePresenter;
import apps.webbisswift.dealsbazaar.base.BaseView;
import apps.webbisswift.dealsbazaar.ui.viewmodels.DealSectionVM;
import io.reactivex.Observable;

/**
 * Created by biswas on 15/05/2017.
 */

public interface CategoryProductsMVPContract {

    interface View extends BaseView {

        /* Deals Loading & Setting methods */
        void showProductsLoading();
        void hideProductsLoading();
        void addProductsSection(DealSectionVM resultSection);
        void productsLoadError(String error);

        void fillAds();
    }

    interface Presenter extends BasePresenter {
        void requestProducts(String main, String sub);
    }

    interface Model{
        Observable<DealSectionVM> loadProducts(String main, String sub);
    }

}
