package apps.webbisswift.dealsbazaar.ui.adapters.RowItems;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.ui.adapters.IRow;

/**
 * Created by biswas on 14/08/2017.
 */

public class NativeAdVM implements IRow{



    private int adType;


    public NativeAdVM(int adType){
        this.adType = adType;
    }


    @Override
    public int getType() {
        return adType;
    }




}
