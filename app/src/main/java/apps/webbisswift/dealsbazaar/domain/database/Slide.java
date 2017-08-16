package apps.webbisswift.dealsbazaar.domain.database;

import com.orm.SugarRecord;

import java.util.List;

import apps.webbisswift.dealsbazaar.Utils.Utils;
import apps.webbisswift.dealsbazaar.domain.net.model.FeaturedSlide;
import io.reactivex.Observable;

/**
 * Created by biswas on 28/03/2017.
 */

public class Slide extends SugarRecord{

     public static final String SYNC_STATE_RESOURCE_NAME = "SLIDES";
    public static final long SYNC_RATE = 60 * 60 * 10 ; /* 60sec * 60 * 10 = 10 hour */

    String mOfferImage;
     String mOfferLink;
     String offerDesc;
     String mStoreLogo;
     String storePath;

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getmOfferImage() {
        return mOfferImage;
    }

    public void setmOfferImage(String mOfferImage) {
        this.mOfferImage = mOfferImage;
    }

    public String getmOfferLink() {
        return mOfferLink;
    }

    public void setmOfferLink(String mOfferLink) {
        this.mOfferLink = mOfferLink;
    }

    public String getOfferDesc() {
        return offerDesc;
    }

    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }

    public String getmStoreLogo() {
        return mStoreLogo;
    }

    public void setmStoreLogo(String mStoreLogo) {
        this.mStoreLogo = mStoreLogo;
    }

    public Slide(){}

    public Slide(String mOfferImage, String mOfferLink, String offerDesc, String mStoreLogo, String storePath) {
          this.mOfferImage = mOfferImage;
          this.mOfferLink = mOfferLink;
          this.offerDesc = offerDesc;
          this.mStoreLogo = mStoreLogo;
          this.storePath = storePath;
     }

     public static Observable<Slide> getSlides(){
          List<Slide> slides = Slide.listAll(Slide.class);
          if(slides.size()>0) {
               System.out.println(slides.size()+" Slides found..");
               return Observable.fromIterable(slides);
          }else return Observable.empty();
     }

     public static void saveSlides(List<FeaturedSlide> stores){
          Slide.deleteAll(Slide.class);
          for(FeaturedSlide s: stores){
              String slideUrl = s.getOfferImage().trim();
              slideUrl = slideUrl.replaceAll(" ","%20");
               Slide vs = new Slide(slideUrl, s.getOfferLink().trim(), s.getOfferDesc().trim(), s.getStoreLogo().trim(), s.getStorePath().trim());
               vs.save();
               System.out.println("::::Saved New Slide:::: "+s.getOfferDesc()+"  Store: "+s.getStorePath());
          }

         //Now update Sync State
     }

    public static boolean needsSyncing(long lastSynced) {
        long difference = Utils.getCurrentTimeStamp() - lastSynced;
        System.out.println("**- Slide Sync Difference -** " + difference +" Last Synced: "+lastSynced+" Current: "+ Utils.getCurrentTimeStamp() );

        if(difference > SYNC_RATE)
            System.out.println("**- Slide Sync Difference -** Needs to Sync");
        else
            System.out.println("**- Slide Sync Difference -** No need to Sync");

        return difference > SYNC_RATE;
    }
}
