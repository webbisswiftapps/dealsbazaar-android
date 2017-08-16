package apps.webbisswift.dealsbazaar.domain.prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by biswas on 28/03/2017.
 */

public class SyncState {

    Context mContext;

    public SyncState(Context mContext){
        this.mContext = mContext;
    }

    public long getLastSyncTimeStamp(String forResource){
        SharedPreferences preferences = mContext.getSharedPreferences("SYNC_STATE",Context.MODE_PRIVATE);
        return preferences.getLong("SYNC_TIME_"+forResource, 0);
    }

    public void setLastSyncTimeStamp(String forResource, long timeStamp){
        SharedPreferences.Editor preferences = mContext.getSharedPreferences("SYNC_STATE",Context.MODE_PRIVATE).edit();
        preferences.putLong("SYNC_TIME_"+forResource, timeStamp);
        preferences.apply();
    }


}
