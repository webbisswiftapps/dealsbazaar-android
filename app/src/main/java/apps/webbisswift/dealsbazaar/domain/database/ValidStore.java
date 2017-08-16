package apps.webbisswift.dealsbazaar.domain.database;

import com.orm.SugarRecord;

import java.util.List;

import apps.webbisswift.dealsbazaar.Utils.Utils;
import apps.webbisswift.dealsbazaar.domain.net.model.Store;
import io.reactivex.Observable;

/**
 * Created by biswas on 28/03/2017.
 */

public class ValidStore extends SugarRecord {

    public static final String SYNC_STATE_RESOURCE_NAME = "STORES";
    public static final long SYNC_RATE = 60 * 60 * 24; /* 24 Hour Sync timeout */

    String storeName;
    String storePath;

    @Override
    public String toString() {
        return storeName+" -- "+storePath;
    }

    public ValidStore(){
    }

    public ValidStore(String name, String path){
        this.storeName = name;
        this.storePath = path;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public static Observable<ValidStore> getValidStores(){
        List<ValidStore> stores = ValidStore.listAll(ValidStore.class);
        if(stores.size()>0) {
            System.out.println(stores.size()+" Stores found..");
            return Observable.fromIterable(stores);
        }else return Observable.empty();
    }

    public static Observable<List<ValidStore>> getValidStoresAtOnce(){
        List<ValidStore> stores = ValidStore.listAll(ValidStore.class);
        if(stores.size() > 0){
            return Observable.just(stores);
        }else return Observable.empty();
    }

    public static void saveStores(List<Store> stores){
        ValidStore.deleteAll(ValidStore.class);
        for(Store s: stores){
            ValidStore vs = new ValidStore(s.getStoreName(), s.getObj());
            vs.save();
        }

    }

    public static boolean needsSyncing(long lastSynced) {
        long difference = Utils.getCurrentTimeStamp() - lastSynced;
        System.out.println("**- Store Sync Difference -** " + difference );
        return difference > SYNC_RATE;
    }

}
