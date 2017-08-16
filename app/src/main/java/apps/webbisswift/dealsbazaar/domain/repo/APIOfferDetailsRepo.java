package apps.webbisswift.dealsbazaar.domain.repo;

import android.content.Context;

import java.io.IOException;

import apps.webbisswift.dealsbazaar.Utils.NetUtils;
import apps.webbisswift.dealsbazaar.domain.net.NepdealsAPI;
import apps.webbisswift.dealsbazaar.domain.net.model.PageResponse;
import apps.webbisswift.dealsbazaar.domain.repo.base.OfferDetailsRepository;
import io.reactivex.Observable;

/**
 * Created by biswas on 29/03/2017.
 */

public class APIOfferDetailsRepo implements OfferDetailsRepository {

    NepdealsAPI api;
    Context context;

    public APIOfferDetailsRepo(NepdealsAPI api, Context context) {
        this.api = api;
        this.context = context;
    }

    @Override
    public Observable<PageResponse> loadOffer(String url, String storePath) {
        if(NetUtils.isOnline(context)){
            return api.loadPage(storePath, url);
        }else return Observable.error(new IOException("Connectivity Error."));
    }
}
