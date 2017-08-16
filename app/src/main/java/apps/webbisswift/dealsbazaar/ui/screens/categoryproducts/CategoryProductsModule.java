package apps.webbisswift.dealsbazaar.ui.screens.categoryproducts;

import android.content.Context;

import javax.inject.Singleton;

import apps.webbisswift.dealsbazaar.domain.net.NepdealsAPI;
import apps.webbisswift.dealsbazaar.domain.repo.APICategoryProductsRepo;
import apps.webbisswift.dealsbazaar.domain.repo.base.CategoryProductsRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.StoreRepository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by biswas on 15/05/2017.
 */

@Module
public class CategoryProductsModule {

    @Provides
    public CategoryProductsMVPContract.Presenter providePresenter(CategoryProductsMVPContract.Model model){
        return new CategoryProductsPresenter(model);
    }

    @Provides
    public CategoryProductsMVPContract.Model provideModel(CategoryProductsRepository catRepo, StoreRepository storeRepository){
        return new CategoryProductsModel(catRepo, storeRepository);
    }

    @Singleton
    @Provides
    public CategoryProductsRepository provideCatRepo(NepdealsAPI api, Context context){
        return new APICategoryProductsRepo(api);
    }

}
