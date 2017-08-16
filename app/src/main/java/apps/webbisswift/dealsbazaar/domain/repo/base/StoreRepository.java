package apps.webbisswift.dealsbazaar.domain.repo.base;

import java.util.List;

import apps.webbisswift.dealsbazaar.domain.database.ValidStore;
import apps.webbisswift.dealsbazaar.domain.net.model.Store;
import io.reactivex.Observable;

/**
 * Created by biswas on 28/03/2017.
 */

public interface StoreRepository {

    public void saveStores(List<Store> stores);
    public Observable<ValidStore> getStores();
    public Observable<List<ValidStore>> getAllStoresAtOnce();

}
