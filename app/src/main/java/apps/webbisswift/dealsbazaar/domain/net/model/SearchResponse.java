package apps.webbisswift.dealsbazaar.domain.net.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by biswas on 01/04/2017.
 */

public class SearchResponse {

    @SerializedName("results")
    private List<Results> mResults;
    @SerializedName("success")
    private Boolean mSuccess;


    public List<Results> getmResults() {
        return mResults;
    }

    public void setmResults(List<Results> mResults) {
        this.mResults = mResults;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
