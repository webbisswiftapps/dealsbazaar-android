package apps.webbisswift.dealsbazaar.ui.screens.splash;

import apps.webbisswift.dealsbazaar.domain.database.ValidStore;
import apps.webbisswift.dealsbazaar.domain.repo.base.StoreRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.ValidCategoriesRepository;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by biswas on 28/03/2017.
 */

public class SplashModel implements SplashMVPContract.Model {

    StoreRepository storeRepo;
    ValidCategoriesRepository categoriesRepo;

    public SplashModel(StoreRepository storeRepo, ValidCategoriesRepository categoriesRepo) {
        this.storeRepo = storeRepo;
        this.categoriesRepo = categoriesRepo;
    }

    @Override
    public Observable<Boolean> updateStores() {
        return storeRepo.getStores()
                .flatMap(new Function<ValidStore, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(ValidStore validStore) throws Exception {
                        return Observable.just(true);
                    }
                });
    }


}
