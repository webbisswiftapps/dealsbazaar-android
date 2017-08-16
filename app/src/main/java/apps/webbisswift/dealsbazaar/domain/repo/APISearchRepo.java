package apps.webbisswift.dealsbazaar.domain.repo;

import android.content.Context;

import java.io.IOException;

import apps.webbisswift.dealsbazaar.Utils.NetUtils;
import apps.webbisswift.dealsbazaar.domain.net.NepdealsAPI;
import apps.webbisswift.dealsbazaar.domain.net.model.SearchResponse;
import apps.webbisswift.dealsbazaar.domain.repo.base.SearchRepository;
import io.reactivex.Observable;

/**
 * Created by biswas on 01/04/2017.
 */

public class APISearchRepo implements SearchRepository {

    NepdealsAPI api;
    Context context;

    public APISearchRepo(NepdealsAPI api, Context context) {
        this.api = api;
        this.context = context;
    }

    @Override
    public Observable<SearchResponse> search(String query) {
        if(NetUtils.isOnline(context)){
            return api.searchPage(query);
        }else return Observable.error(new IOException("Connectivity Error."));
    }

    @Override
    public Observable<SearchResponse> search(String query, String storePath) {
        if(NetUtils.isOnline(context)){
            return api.searchPage(query, storePath);
        }else return Observable.error(new IOException("Connectivity Error."));
    }
}
