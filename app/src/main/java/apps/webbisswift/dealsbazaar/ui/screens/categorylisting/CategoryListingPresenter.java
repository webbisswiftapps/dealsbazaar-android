package apps.webbisswift.dealsbazaar.ui.screens.categorylisting;

import android.support.annotation.Nullable;



import java.util.List;

import apps.webbisswift.dealsbazaar.base.BaseView;
import apps.webbisswift.dealsbazaar.ui.viewmodels.CategoryVM;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by biswas on 15/05/2017.
 */

public class CategoryListingPresenter implements CategoryListingMVPContract.Presenter {

    @Nullable
    CategoryListingMVPContract.View mView;
    CategoryListingMVPContract.Model mModel;
    Disposable categorySubscription;

    public CategoryListingPresenter(CategoryListingMVPContract.Model mModel) {
        this.mModel = mModel;
    }

    @Override
    public void getCategories(String forKey) {
        if(mView!=null){
            mView.showLoading();
        }
        categorySubscription = this.mModel.getCategories(forKey)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableObserver<List<CategoryVM>>(){
                                    @Override
                                    public void onNext(List<CategoryVM> value) {
                                        if(mView!=null){
                                            mView.setCategories(value);
                                            mView.showList();
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

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
        if(categorySubscription!=null && !categorySubscription.isDisposed())
            categorySubscription.dispose();

        this.mView = null;
        this.mModel = null;
    }

    @Override
    public void attachView(BaseView view) {
        this.mView = (CategoryListingMVPContract.View) view;
    }
}
