package apps.webbisswift.dealsbazaar.domain.repo.base;

import apps.webbisswift.dealsbazaar.domain.net.model.DealSection;
import io.reactivex.Observable;

/**
 * Created by biswas on 26/03/2017.
 */

public interface DealsRepository {
    public Observable<DealSection> loadDeals();
    public Observable<DealSection> loadDeals(String storePath);
}
