package apps.webbisswift.dealsbazaar.ui.viewmodels;

import java.util.List;

import apps.webbisswift.dealsbazaar.ui.adapters.IRow;

/**
 * Created by biswas on 26/03/2017.
 */

public class DealSectionVM  implements IRow{

    private long itemCount;
    private String dealTitle;
    private String storeName;
    private String storeLogoURL;
    private List<ProductVM> items;
    private String nextPageURL;
    private String storePath;
    private int weight = 0;

    /* For native Ads */
    private boolean isAd = false;

    public boolean isAd() {
        return isAd;
    }

    public void setAd(boolean ad) {
        isAd = ad;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void increaseWeight(int by){
        this.weight += by;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getNextPageURL() {
        return nextPageURL;
    }

    public void setNextPageURL(String nextPageURL) {
        this.nextPageURL = nextPageURL;
    }

    public long getItemCount() {
        return itemCount;
    }

    public void setItemCount(long itemCount) {
        this.itemCount = itemCount;
    }

    public String getDealTitle() {
        return dealTitle;
    }

    public void setDealTitle(String dealTitle) {
        this.dealTitle = dealTitle;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLogoURL() {
        return storeLogoURL;
    }

    public void setStoreLogoURL(String storeURL) {
        this.storeLogoURL = storeURL;
    }

    public List<ProductVM> getItems() {
        return items;
    }

    public void setItems(List<ProductVM> items) {
        this.items = items;
    }


    /* IROW implementation */

    @Override
    public int getType() {
        return IRow.TYPE_SECTION;
    }
}
