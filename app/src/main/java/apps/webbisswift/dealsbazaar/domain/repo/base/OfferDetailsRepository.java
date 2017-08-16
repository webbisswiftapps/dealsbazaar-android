package apps.webbisswift.dealsbazaar.domain.repo.base;

import apps.webbisswift.dealsbazaar.domain.net.model.PageResponse;
import io.reactivex.Observable;

/**
 * Created by biswas on 29/03/2017.
 */

public interface OfferDetailsRepository {
    public Observable<PageResponse> loadOffer(String url, String storePath);
}
