
package apps.webbisswift.dealsbazaar.domain.net.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Results {

    @SerializedName("count")
    private Long mCount;
    @SerializedName("listing_title")
    private String mListingTitle;
    @SerializedName("products")
    private List<Product> mProducts;
    @SerializedName("store")
    private String mStore;
    @SerializedName("store_logo")
    private String mStoreLogo;
    @SerializedName("store_path")
    private String mStorePath;

    @SerializedName("next_page")
    private String nextPageURL;

    public Long getCount() {
        return mCount;
    }

    public void setCount(Long count) {
        mCount = count;
    }

    public String getListingTitle() {
        return mListingTitle;
    }

    public void setListingTitle(String listing_title) {
        mListingTitle = listing_title;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    public String getStore() {
        return mStore;
    }

    public void setStore(String store) {
        mStore = store;
    }

    public String getStoreLogo() {
        return mStoreLogo;
    }

    public void setStoreLogo(String store_logo) {
        mStoreLogo = store_logo;
    }

    public String getStorePath() {
        return mStorePath;
    }

    public void setStorePath(String store_path) {
        mStorePath = store_path;
    }


    public String getNextPageURL() {
        return nextPageURL;
    }

    public void setNextPageURL(String nextPageURL) {
        this.nextPageURL = nextPageURL;
    }
}
