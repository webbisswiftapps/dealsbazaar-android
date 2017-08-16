package apps.webbisswift.dealsbazaar.domain.repo;

import apps.webbisswift.dealsbazaar.domain.database.WishlistProduct;
import apps.webbisswift.dealsbazaar.domain.repo.base.WishlistRepository;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import io.reactivex.Observable;

/**
 * Created by biswas on 02/07/2017.
 */

public class DBWishlistRepo implements WishlistRepository {

    @Override
    public void saveProduct(ProductVM product) {
        WishlistProduct.save(product);
    }

    @Override
    public void removeFromWishList(ProductVM productVM) {
        WishlistProduct.removeFromWishlist(productVM);
    }

    @Override
    public Observable<WishlistProduct> getWishlistProducts() {
        return WishlistProduct.getWishlistProducts();
    }
}
