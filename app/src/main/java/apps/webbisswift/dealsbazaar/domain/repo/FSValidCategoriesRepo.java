package apps.webbisswift.dealsbazaar.domain.repo;

import java.util.List;

import apps.webbisswift.dealsbazaar.Utils.Utils;
import apps.webbisswift.dealsbazaar.domain.net.NepdealsAPI;
import apps.webbisswift.dealsbazaar.domain.net.model.Category;
import apps.webbisswift.dealsbazaar.domain.net.model.ValidCategoriesResponse;
import apps.webbisswift.dealsbazaar.domain.prefs.SyncState;
import apps.webbisswift.dealsbazaar.domain.prefs.ValidCategoryCache;
import apps.webbisswift.dealsbazaar.domain.repo.base.ValidCategoriesRepository;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by biswas on 14/05/2017.
 */

public class FSValidCategoriesRepo implements ValidCategoriesRepository {

    NepdealsAPI api;
    SyncState syncState;
    ValidCategoryCache validCategoryCache;


    public FSValidCategoriesRepo(NepdealsAPI api, SyncState syncState, ValidCategoryCache cache) {
        this.api = api;
        this.syncState = syncState;
        this.validCategoryCache = cache;
    }


    @Override
    public Observable<List<Category>> getCategories(String main) {
        if(!ValidCategoryCache.needsSyncing(this.syncState.getLastSyncTimeStamp("VALID_CATEGORY_"+main))) {
            return this.validCategoryCache.readCategories(main)
                    .switchIfEmpty(loadCategoriesFromApi(main));
        }else{
            return loadCategoriesFromApi(main);
        }
    }


    private Observable<List<Category>> loadCategoriesFromApi(final String main){
        return api.getValidCategories(main)
                .flatMap(new Function<ValidCategoriesResponse, ObservableSource<List<Category>>>() {
                    @Override
                    public ObservableSource<List<Category>> apply(ValidCategoriesResponse validCategoriesResponse) throws Exception {
                        // 1st save the response to cache
                        if(validCategoryCache.saveCategories(main, validCategoriesResponse.toJson())) {
                            syncState.setLastSyncTimeStamp("VALID_CATEGORY_"+main, Utils.getCurrentTimeStamp());
                        }
                        return Observable.just(validCategoriesResponse.getCategories());
                    }
                });
    }

}
