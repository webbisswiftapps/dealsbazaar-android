package apps.webbisswift.dealsbazaar.domain.repo;

import java.util.List;

import apps.webbisswift.dealsbazaar.Utils.Utils;
import apps.webbisswift.dealsbazaar.domain.database.Slide;
import apps.webbisswift.dealsbazaar.domain.net.NepdealsAPI;
import apps.webbisswift.dealsbazaar.domain.net.exceptions.NoSlidesFoundException;
import apps.webbisswift.dealsbazaar.domain.net.model.FeaturedSlide;
import apps.webbisswift.dealsbazaar.domain.net.model.FeaturedSlidesResponse;
import apps.webbisswift.dealsbazaar.domain.prefs.SyncState;
import apps.webbisswift.dealsbazaar.domain.repo.base.SlidesRepository;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by biswas on 25/03/2017.
 */

public class APISlidesRepo implements SlidesRepository {

    NepdealsAPI api;
    SyncState syncState;


    public APISlidesRepo(NepdealsAPI api, SyncState syncState) {
        this.api = api;
        this.syncState = syncState;
    }


    @Override
    public Observable<Slide> loadSlides() {

        long slideLastSynced = this.syncState.getLastSyncTimeStamp(Slide.SYNC_STATE_RESOURCE_NAME);

        if(!Slide.needsSyncing(slideLastSynced)) {
            return Slide.getSlides()
                    .switchIfEmpty(loadSlidesFromAPI());
        }else{
            return loadSlidesFromAPI();
        }
    }


    private Observable<Slide> loadSlidesFromAPI(){
        return api.loadFeaturedSlides()
                .flatMap(new Function<FeaturedSlidesResponse, ObservableSource<List<FeaturedSlide>>>() {
                    @Override
                    public ObservableSource<List<FeaturedSlide>> apply(FeaturedSlidesResponse featuredSlidesResponse) throws Exception {
                        if (featuredSlidesResponse.getCount() > 0) {
                            return Observable.just(featuredSlidesResponse.getSlides());
                        } else {
                            return Observable.error(new NoSlidesFoundException());
                        }
                    }
                }).flatMap(new Function<List<FeaturedSlide>, ObservableSource<Slide>>() {
            @Override
            public ObservableSource<Slide> apply(List<FeaturedSlide> featuredSlides) throws Exception {
                Slide.saveSlides(featuredSlides);
                syncState.setLastSyncTimeStamp(Slide.SYNC_STATE_RESOURCE_NAME, Utils.getCurrentTimeStamp());
                return Slide.getSlides();
            }
        });

    }



}
