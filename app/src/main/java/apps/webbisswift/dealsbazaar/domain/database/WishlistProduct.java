package apps.webbisswift.dealsbazaar.domain.database;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

import apps.webbisswift.dealsbazaar.Utils.Utils;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import io.reactivex.Observable;

/**
 * Created by biswas on 02/07/2017.
 */

public class WishlistProduct extends SugarRecord {

    private String productDiscount;
    private String productImageURL;
    private String productName;
    private String productPrice;
    private String productOldPrice;
    private String productURL;
    private String productStore;
    private String productStoreLogo;
    private int productDiscountValue;

    public WishlistProduct(ProductVM productVM){
        this.productDiscount = productVM.getProductDiscount();
        this.productImageURL = productVM.getProductImageURL();
        this.productName = productVM.getProductName();
        this.productPrice = productVM.getProductPrice();
        this.productOldPrice = productVM.getProductOldPrice();
        this.productURL = productVM.getProductURL();
        this.productStore = productVM.getProductStore();
        this.productStoreLogo = productVM.getProductStoreLogo();
        this.productDiscountValue = productVM.getProductDiscountValue();
    }

    public static Observable<WishlistProduct> getWishlistProducts(){
        List<WishlistProduct> stores = WishlistProduct.listAll(WishlistProduct.class);
        if(stores.size()>0) {
            System.out.println(stores.size()+" Wishlist products found..");
            return Observable.fromIterable(stores);
        }else return Observable.empty();
    }

    public static void saveToWishlist(ProductVM product){
        WishlistProduct productW = new WishlistProduct(product);
        productW.save();
    }

    public static void removeFromWishlist(ProductVM product){
        WishlistProduct.deleteAll(WishlistProduct.class, "product_url = ?", product.getProductURL());
    }


}
