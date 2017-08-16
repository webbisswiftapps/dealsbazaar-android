package apps.webbisswift.dealsbazaar.domain.repo;

import java.util.List;

import apps.webbisswift.dealsbazaar.Utils.Utils;
import apps.webbisswift.dealsbazaar.domain.database.ValidStore;
import apps.webbisswift.dealsbazaar.domain.net.NepdealsAPI;
import apps.webbisswift.dealsbazaar.domain.net.model.Store;
import apps.webbisswift.dealsbazaar.domain.prefs.SyncState;
import apps.webbisswift.dealsbazaar.domain.repo.base.StoreRepository;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by biswas on 28/03/2017.
 */

public class DBStoreRepo implements StoreRepository {

    NepdealsAPI api;
    SyncState syncState;


    public DBStoreRepo(NepdealsAPI api, SyncState syncState) {
        this.api = api;
        this.syncState = syncState;
    }



    @Override
    public void saveStores(List<Store> stores) {
        ValidStore.saveStores(stores);
    }




    @Override
    public Observable<ValidStore> getStores() {
        if(!ValidStore.needsSyncing(this.syncState.getLastSyncTimeStamp(ValidStore.SYNC_STATE_RESOURCE_NAME))) {
            return ValidStore.getValidStores()
                    .switchIfEmpty(loadStoreFromApi());
        }else{
            return loadStoreFromApi();
        }
    }


    private Observable<ValidStore> loadStoreFromApi(){
        return api.loadValidStores().flatMap(new Function<List<Store>, ObservableSource<ValidStore>>() {
            @Override
            public ObservableSource<ValidStore> apply(List<Store> stores) throws Exception {
                ValidStore.saveStores(stores);
                syncState.setLastSyncTimeStamp(ValidStore.SYNC_STATE_RESOURCE_NAME, Utils.getCurrentTimeStamp());
                return ValidStore.getValidStores();
            }
        });
    }

    private Observable<List<ValidStore>> loadStoreFormApiAtOnce(){
        return api.loadValidStores().flatMap(new Function<List<Store>, ObservableSource<List<ValidStore>>>() {
            @Override
            public ObservableSource<List<ValidStore>> apply(List<Store> stores) throws Exception {
                ValidStore.saveStores(stores);
                syncState.setLastSyncTimeStamp(ValidStore.SYNC_STATE_RESOURCE_NAME, Utils.getCurrentTimeStamp());
                return ValidStore.getValidStoresAtOnce();
            }
        });
    }

    @Override
    public Observable<List<ValidStore>> getAllStoresAtOnce() {
        if(!ValidStore.needsSyncing(this.syncState.getLastSyncTimeStamp(ValidStore.SYNC_STATE_RESOURCE_NAME))) {
            return ValidStore.getValidStoresAtOnce()
                    .switchIfEmpty(loadStoreFormApiAtOnce());
        }else{
            return loadStoreFormApiAtOnce();
        }
    }
}
