package apps.webbisswift.dealsbazaar.ui.screens.productsearch;

import java.util.List;

import apps.webbisswift.dealsbazaar.base.BasePresenter;
import apps.webbisswift.dealsbazaar.base.BaseView;
import apps.webbisswift.dealsbazaar.domain.database.SearchHistory;
import apps.webbisswift.dealsbazaar.ui.viewmodels.DealSectionVM;
import io.reactivex.Observable;

/**
 * Created by biswas on 01/04/2017.
 */

public interface ProductSearchMVPContract {

    interface View extends BaseView {


        /* Deals Loading & Setting methods */
        void showProductsLoading();
        void hideProductsLoading();
        void addSearchResultSection(DealSectionVM resultSection);
        void productsLoadError(String error);

        void fillAds();

        /* Search History set control */
        void setSearchHistory(List<SearchHistory> searchHistory);
    }

    interface Presenter extends BasePresenter {
        void requestProducts(String query);
        void cancelPreviousRequest();
        void requestSearchHistory();
        void insertSearchHistory(String query);
    }

    interface Model{
        Observable<DealSectionVM> loadProducts(String query);
        Observable<List<SearchHistory>> loadSearchHistory();
        Observable<Boolean> insertSearchHistory(String query);
    }
}
