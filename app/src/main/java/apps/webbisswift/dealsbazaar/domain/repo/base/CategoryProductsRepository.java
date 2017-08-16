package apps.webbisswift.dealsbazaar.domain.repo.base;

import apps.webbisswift.dealsbazaar.domain.net.model.PageResponse;
import io.reactivex.Observable;

/**
 * Created by biswas on 15/05/2017.
 */

public interface CategoryProductsRepository {
    public Observable<PageResponse> loadProducts(String main, String sub, String storePath);
}
