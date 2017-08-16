package apps.webbisswift.dealsbazaar.domain.repo;

import apps.webbisswift.dealsbazaar.domain.net.NepdealsAPI;
import apps.webbisswift.dealsbazaar.domain.net.model.PageResponse;
import apps.webbisswift.dealsbazaar.domain.repo.base.CategoryProductsRepository;
import io.reactivex.Observable;

/**
 * Created by biswas on 15/05/2017.
 */

public class APICategoryProductsRepo implements CategoryProductsRepository {

    NepdealsAPI api;

    public APICategoryProductsRepo(NepdealsAPI api){
        this.api = api;
    }

    @Override
    public Observable<PageResponse> loadProducts(String main, String sub, String storePath) {
        return this.api.loadProducts(main, sub, storePath);
    }
}
