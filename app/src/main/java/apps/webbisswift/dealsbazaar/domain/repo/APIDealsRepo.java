package apps.webbisswift.dealsbazaar.domain.repo;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import apps.webbisswift.dealsbazaar.Utils.NetUtils;
import apps.webbisswift.dealsbazaar.domain.net.NepdealsAPI;
import apps.webbisswift.dealsbazaar.domain.net.model.DealSection;
import apps.webbisswift.dealsbazaar.domain.repo.base.DealsRepository;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by biswas on 26/03/2017.
 */

public class APIDealsRepo implements DealsRepository {

    NepdealsAPI api;
    Context context;

    public APIDealsRepo(NepdealsAPI api, Context context) {
        this.api = api;
        this.context = context;
    }


    @Override
    public Observable<DealSection> loadDeals() {

        if(NetUtils.isOnline(this.context)) {
            return api.loadDeals()
                    .flatMap(new Function<List<DealSection>, ObservableSource<DealSection>>() {
                        @Override
                        public ObservableSource<DealSection> apply(List<DealSection> deals) throws Exception {
                            return Observable.fromIterable(deals);
                        }
                    });
        }else return Observable.error(new IOException("Network Unavailable."));
    }

    @Override
    public Observable<DealSection> loadDeals(String storePath) {
        if(NetUtils.isOnline(this.context)) {
            return api.loadDeals(storePath)
                    .flatMap(new Function<List<DealSection>, ObservableSource<DealSection>>() {
                        @Override
                        public ObservableSource<DealSection> apply(List<DealSection> featuredSlides) throws Exception {
                            return Observable.fromIterable(featuredSlides);
                        }
                    });
        }else return Observable.error(new IOException("Network Unavailable."));
    }
}
