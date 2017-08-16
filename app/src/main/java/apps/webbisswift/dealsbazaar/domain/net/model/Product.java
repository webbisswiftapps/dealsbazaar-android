
package apps.webbisswift.dealsbazaar.domain.net.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Product {

    @SerializedName("converted")
    private Boolean mConverted;
    @SerializedName("product_discount")
    private String mProductDiscount;
    @SerializedName("product_image")
    private String mProductImage;
    @SerializedName("product_link")
    private String mProductLink;
    @SerializedName("product_name")
    private String mProductName;
    @SerializedName("product_new_price")
    private String mProductNewPrice;
    @SerializedName("product_old_price")
    private String mProductOldPrice;
    @SerializedName("product_price")
    private String mProductPrice;

    private String productStore;
    private String productStoreURL;

    public Boolean getConverted() {
        return mConverted;
    }

    public void setConverted(Boolean converted) {
        mConverted = converted;
    }

    public String getProductDiscount() {
        return mProductDiscount;
    }

    public void setProductDiscount(String product_discount) {
        mProductDiscount = product_discount;
    }

    public String getProductImage() {
        return mProductImage;
    }

    public void setProductImage(String product_image) {
        mProductImage = product_image;
    }

    public String getProductLink() {
        return mProductLink;
    }

    public void setProductLink(String product_link) {
        mProductLink = product_link;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String product_name) {
        mProductName = product_name;
    }

    public String getProductNewPrice() {
        return mProductNewPrice;
    }

    public void setProductNewPrice(String product_new_price) {
        mProductNewPrice = product_new_price;
    }

    public String getProductOldPrice() {
        return mProductOldPrice;
    }

    public void setProductOldPrice(String product_old_price) {
        mProductOldPrice = product_old_price;
    }

    public String getProductPrice() {
        return mProductPrice;
    }

    public void setProductPrice(String product_price) {
        mProductPrice = product_price;
    }


    public String getProductStoreURL() {
        return productStoreURL;
    }

    public void setProductStoreURL(String productStoreURL) {
        this.productStoreURL = productStoreURL;
    }

    public String getProductStore() {
        return productStore;
    }

    public void setProductStore(String productStore) {
        this.productStore = productStore;
    }
}
