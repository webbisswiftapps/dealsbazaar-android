
package apps.webbisswift.dealsbazaar.domain.net.model;

import com.google.gson.annotations.SerializedName;

public class Store {

    @SerializedName("obj")
    private String storePath;
    @SerializedName("store_name")
    private String mStoreName;

    public String getObj() {
        return storePath;
    }

    public void setObj(String obj) {
        storePath = obj;
    }

    public String getStoreName() {
        return mStoreName;
    }

    public void setStoreName(String store_name) {
        mStoreName = store_name;
    }

}
