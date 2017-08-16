
package apps.webbisswift.dealsbazaar.domain.net.model;

import com.google.gson.annotations.SerializedName;


public class PageResponse {

    @SerializedName("results")
    private Results mResults;
    @SerializedName("success")
    private Boolean mSuccess;


    public Results getResults() {
        return mResults;
    }

    public void setResults(Results results) {
        mResults = results;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
