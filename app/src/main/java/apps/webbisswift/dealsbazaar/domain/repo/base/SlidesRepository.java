package apps.webbisswift.dealsbazaar.domain.repo.base;

import apps.webbisswift.dealsbazaar.domain.database.Slide;
import io.reactivex.Observable;

/**
 * Created by biswas on 25/03/2017.
 */

public interface SlidesRepository {

    public Observable<Slide> loadSlides();

}
