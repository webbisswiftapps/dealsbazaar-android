
package apps.webbisswift.dealsbazaar.domain.net.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeaturedSlidesResponse {

    @SerializedName("count")
    private Long mCount;
    @SerializedName("results")
    private List<FeaturedSlide> mResults;

    public Long getCount() {
        return mCount;
    }

    public void setCount(Long count) {
        mCount = count;
    }

    public List<FeaturedSlide> getSlides() {
        return mResults;
    }

    public void setSlides(List<FeaturedSlide> results) {
        mResults = results;
    }

}
