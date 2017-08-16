
package apps.webbisswift.dealsbazaar.domain.net.model;

import com.google.gson.annotations.SerializedName;


public class FeaturedSlide {

    @SerializedName("offer_image")
    private String mOfferImage;
    @SerializedName("offer_link")
    private String mOfferLink;
    @SerializedName("offer_desc")
    private String offerDesc;
    @SerializedName("store_logo")
    private String mStoreLogo;
    @SerializedName("store_path")
    private String storePath;

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getOfferImage() {
        return mOfferImage;
    }

    public void setOfferImage(String offer_image) {
        mOfferImage = offer_image;
    }

    public String getOfferLink() {
        return mOfferLink;
    }

    public void setOfferLink(String offer_link) {
        mOfferLink = offer_link;
    }

    public String getOfferDesc() {
        return offerDesc;
    }

    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }

    public String getStoreLogo() {
        return mStoreLogo;
    }

    public void setStoreLogo(String store_logo) {
        mStoreLogo = store_logo;
    }

}
