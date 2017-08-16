package apps.webbisswift.dealsbazaar.domain.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import apps.webbisswift.dealsbazaar.Utils.Utils;
import apps.webbisswift.dealsbazaar.domain.net.model.Category;
import apps.webbisswift.dealsbazaar.domain.net.model.ValidCategoriesResponse;
import io.reactivex.Observable;

/**
 * Created by biswas on 14/05/2017.
 */

public class ValidCategoryCache {

    public static final long SYNC_RATE = 60 * 60; /* 1 Hour Sync timeout */

    Context mContext;

    public ValidCategoryCache(Context mContext){
        this.mContext = mContext;
    }



    /* This function saves the categories json file for a given main category */
    public boolean saveCategories(String key, String json){

            SharedPreferences.Editor preferences = mContext.getSharedPreferences("CATEGORIES", Context.MODE_PRIVATE).edit();
            preferences.putString(key, json);
            preferences.apply();
            return true;

    }

    /* This function reads the categories json file from the cache for a given main category */
    public Observable<List<Category>> readCategories(String key){
        SharedPreferences preferences = mContext.getSharedPreferences("CATEGORIES",Context.MODE_PRIVATE);
        String json= preferences.getString(key, "");

        if(!json.isEmpty()){
            ValidCategoriesResponse response = ValidCategoriesResponse.parseWith(json);

            if(response.getCategories()!=null && response.getCategories().size() > 0){
                return Observable.just(response.getCategories());
            }else{
                return Observable.empty();
            }

        }else return Observable.empty();
    }

    public static boolean needsSyncing(long lastSynced) {
        long difference = Utils.getCurrentTimeStamp() - lastSynced;
        System.out.println("**- Store Sync Difference -** " + difference );
        return difference > SYNC_RATE;
    }
}
