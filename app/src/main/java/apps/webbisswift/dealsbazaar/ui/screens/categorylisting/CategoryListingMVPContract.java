package apps.webbisswift.dealsbazaar.ui.screens.categorylisting;


import java.util.List;

import apps.webbisswift.dealsbazaar.base.BasePresenter;
import apps.webbisswift.dealsbazaar.base.BaseView;
import apps.webbisswift.dealsbazaar.ui.viewmodels.CategoryVM;
import io.reactivex.Observable;

/**
 * Created by biswas on 14/05/2017.
 */

public interface CategoryListingMVPContract {

    interface View extends BaseView {

        /* Listing Methods*/
        void showLoading();
        void showList();
        void setCategories(List<CategoryVM> categories);

        /* Ad Loading */
        void showTopBanner();
        void hideTopBanner();

    }

    interface Presenter extends BasePresenter {
        void getCategories(String forKey);


    }

    interface Model{
        Observable<List<CategoryVM>> getCategories(String forKey);
    }
}
