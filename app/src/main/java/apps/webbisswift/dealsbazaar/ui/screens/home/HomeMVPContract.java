package apps.webbisswift.dealsbazaar.ui.screens.home;


import java.util.List;

import apps.webbisswift.dealsbazaar.base.BasePresenter;
import apps.webbisswift.dealsbazaar.base.BaseView;
import apps.webbisswift.dealsbazaar.domain.database.SearchHistory;
import apps.webbisswift.dealsbazaar.ui.viewmodels.DealSectionVM;
import apps.webbisswift.dealsbazaar.ui.viewmodels.SlideVM;
import io.reactivex.Observable;

/**
 * Created by biswas on 25/03/2017.
 */

public interface HomeMVPContract {

    interface View extends BaseView {

        /* Slide Loading & Setting methods*/
        void showSlidesLoading();
        void hideSlidesLoading();
        void addSlide(SlideVM slide);
        void showSlideError(String error);

        /* Deals Loading & Setting methods */
        void showDealsLoading();
        void hideDealsLoading();
        void addDealSection(DealSectionVM dealsection);
        void dealLoadError(String error);

        /* Search History set control */
        void setSearchHistory(List<SearchHistory> searchHistory);

        /* Ad Loading */
        void fillAdsInList();

    }

    interface Presenter extends BasePresenter {
        void requestSlides();
        void requestDeals();
        void requestSearchHistory();
        void requestRecommendedProducts(String query);
        void insertSearchHistory(String query);


    }

    interface Model{
        Observable<SlideVM> loadSlides();
        Observable<DealSectionVM> loadDeals();
        Observable<DealSectionVM> loadDeals(String storePath);
        Observable<List<SearchHistory>> loadSearchHistory();
        Observable<Boolean> insertSearchHistory(String query);
        Observable<DealSectionVM> loadRecommendedProducts(String query);
    }

}
