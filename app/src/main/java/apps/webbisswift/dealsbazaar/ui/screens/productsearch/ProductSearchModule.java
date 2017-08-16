package apps.webbisswift.dealsbazaar.ui.screens.productsearch;

import android.content.Context;

import javax.inject.Singleton;

import apps.webbisswift.dealsbazaar.domain.net.NepdealsAPI;
import apps.webbisswift.dealsbazaar.domain.repo.APISearchRepo;
import apps.webbisswift.dealsbazaar.domain.repo.base.SearchHistoryRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.SearchRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.StoreRepository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by biswas on 01/04/2017.
 */

@Module
public class ProductSearchModule {

    @Provides
    public ProductSearchMVPContract.Presenter providePresenter(ProductSearchMVPContract.Model model){
        return new ProductSearchPresenter(model);
    }

    @Provides
    public ProductSearchMVPContract.Model provideModel(SearchRepository repository, StoreRepository storeRepository, SearchHistoryRepository searchHistoryRepo){
        return new ProductSearchModel(repository, storeRepository, searchHistoryRepo);
    }

    @Singleton
    @Provides
    public SearchRepository provideSearchRepo(NepdealsAPI api, Context context){
        return new APISearchRepo(api, context);
    }


}
