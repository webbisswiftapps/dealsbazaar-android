package apps.webbisswift.dealsbazaar.ui.screens.splash;

import apps.webbisswift.dealsbazaar.domain.net.NepdealsAPI;
import apps.webbisswift.dealsbazaar.domain.prefs.SyncState;
import apps.webbisswift.dealsbazaar.domain.prefs.ValidCategoryCache;
import apps.webbisswift.dealsbazaar.domain.repo.DBStoreRepo;
import apps.webbisswift.dealsbazaar.domain.repo.FSValidCategoriesRepo;
import apps.webbisswift.dealsbazaar.domain.repo.base.StoreRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.ValidCategoriesRepository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by biswas on 28/03/2017.
 */


@Module
public class SplashModule {

    @Provides
    public SplashMVPContract.Presenter providePresenter(SplashMVPContract.Model model){
        return new SplashPresenter(model);
    }

    @Provides
    public SplashMVPContract.Model provideModel(StoreRepository repository, ValidCategoriesRepository categoriesRepository){
        return new SplashModel(repository, categoriesRepository);
    }

    @Provides
    public StoreRepository provideStoreRepo(NepdealsAPI api, SyncState syncState){
        return new DBStoreRepo(api, syncState);
    }

    @Provides
    public ValidCategoriesRepository provideCategoriesRepo(NepdealsAPI api, SyncState syncState, ValidCategoryCache cache){
        return new FSValidCategoriesRepo(api, syncState, cache);
    }


}