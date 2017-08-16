package apps.webbisswift.dealsbazaar.ui.screens.home;

import android.content.Context;

import javax.inject.Singleton;

import apps.webbisswift.dealsbazaar.domain.net.NepdealsAPI;
import apps.webbisswift.dealsbazaar.domain.prefs.SyncState;
import apps.webbisswift.dealsbazaar.domain.repo.APIDealsRepo;
import apps.webbisswift.dealsbazaar.domain.repo.APISlidesRepo;
import apps.webbisswift.dealsbazaar.domain.repo.SearchHistoryRepo;
import apps.webbisswift.dealsbazaar.domain.repo.base.DealsRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.SearchHistoryRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.SearchRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.SlidesRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.StoreRepository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by biswas on 25/03/2017.
 */

@Module
public class HomeModule {

    @Provides
    public HomeMVPContract.Presenter providePresenter(HomeMVPContract.Model model){
        return new HomePresenter(model);
    }

    @Provides
    public HomeMVPContract.Model provideModel(SlidesRepository repository, DealsRepository dealsRepo, StoreRepository storeRepository,
                                              SearchHistoryRepository searchHistoryRepository,
                                              SearchRepository searchRepository){
        return new HomeModel(repository, dealsRepo, storeRepository, searchHistoryRepository,searchRepository);
    }

    @Singleton
    @Provides
    public SlidesRepository provideSlideRepo(NepdealsAPI api, SyncState state){
        return new APISlidesRepo(api, state);
    }

    @Singleton
    @Provides
    public DealsRepository provideDealsRepo(NepdealsAPI api, Context context){ return new APIDealsRepo(api, context);}

    @Singleton
    @Provides
    public SearchHistoryRepository provideSearchHistory(){
        return  new SearchHistoryRepo();
    }

}
