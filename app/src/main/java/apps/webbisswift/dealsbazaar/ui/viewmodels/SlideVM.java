package apps.webbisswift.dealsbazaar.ui.viewmodels;

/**
 * Created by biswas on 25/03/2017.
 */

public class SlideVM {

    /* Simple POJO Class for Slides */

    private String slideURL;
    private String slideFilePath;
    private String slideDescription;
    private String storeLogoURL;
    private String offerURL;
    private String offerStorePath;


    public String getOfferStorePath() {
        return offerStorePath;
    }

    public void setOfferStorePath(String offerStorePath) {
        this.offerStorePath = offerStorePath;
    }

    public String getSlideURL() {
        return slideURL;
    }

    public void setSlideURL(String slideURL) {
        this.slideURL = slideURL;
    }

    public String getSlideFilePath() {
        return slideFilePath;
    }

    public void setSlideFilePath(String slideFilePath) {
        this.slideFilePath = slideFilePath;
    }

    public String getSlideDescription() {
        return slideDescription;
    }

    public void setSlideDescription(String slideDescription) {
        this.slideDescription = slideDescription;
    }

    public String getStoreLogoURL() {
        return storeLogoURL;
    }

    public void setStoreLogoURL(String storeLogoURL) {
        this.storeLogoURL = storeLogoURL;
    }

    public String getOfferURL() {
        return offerURL;
    }

    public void setOfferURL(String offerURL) {
        this.offerURL = offerURL;
    }
}
