package apps.webbisswift.dealsbazaar.domain.repo.base;

import java.util.List;

import apps.webbisswift.dealsbazaar.domain.database.ValidStore;
import apps.webbisswift.dealsbazaar.domain.database.WishlistProduct;
import apps.webbisswift.dealsbazaar.domain.net.model.Store;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import io.reactivex.Observable;

/**
 * Created by biswas on 02/07/2017.
 */

public interface WishlistRepository {
    public void saveProduct(ProductVM product);
    public void removeFromWishList(ProductVM productVM);
    public Observable<WishlistProduct> getWishlistProducts();

}
