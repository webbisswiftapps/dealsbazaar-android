package apps.webbisswift.dealsbazaar.ui.viewmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by biswas on 26/03/2017.
 */

public class ProductVM implements Parcelable {



    private String productDiscount;
    private String productImageURL;
    private String productName;
    private String productPrice;
    private String productOldPrice;
    private String productURL;
    private String productStore;
    private String productStoreLogo;
    private int productDiscountValue;
    private int weight = 0 ;



    public int getProductDiscountValue() {
        return productDiscountValue;
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

    public void setProductDiscountValue(int productDiscountValue) {
        this.productDiscountValue = productDiscountValue;
    }

    public String getProductURL() {
        return productURL;
    }

    public void setProductURL(String productURL) {
        this.productURL = productURL;
    }

    public String getProductDiscount() {
        return productDiscount;
    }


    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getProductImageURL() {
        return productImageURL;
    }

    public void setProductImageURL(String productImageURL) {
        this.productImageURL = productImageURL;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductOldPrice() {
        return productOldPrice;
    }

    public void setProductOldPrice(String productOldPrice) {
        this.productOldPrice = productOldPrice;
    }

    public String getProductStoreLogo() {
        return productStoreLogo;
    }

    public void setProductStoreLogo(String productStoreLogo) {
        this.productStoreLogo = productStoreLogo;
    }

    public String getProductStore() {
        return productStore;
    }

    public void setProductStore(String productStore) {
        this.productStore = productStore;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productDiscount);
        dest.writeString(this.productImageURL);
        dest.writeString(this.productName);
        dest.writeString(this.productPrice);
        dest.writeString(this.productOldPrice);
        dest.writeString(this.productURL);
        dest.writeString(this.productStore);
        dest.writeString(this.productStoreLogo);
    }

    public ProductVM() {
    }

    protected ProductVM(Parcel in) {
        this.productDiscount = in.readString();
        this.productImageURL = in.readString();
        this.productName = in.readString();
        this.productPrice = in.readString();
        this.productOldPrice = in.readString();
        this.productURL = in.readString();
        this.productStore = in.readString();
        this.productStoreLogo = in.readString();
    }

    public static final Parcelable.Creator<ProductVM> CREATOR = new Parcelable.Creator<ProductVM>() {
        @Override
        public ProductVM createFromParcel(Parcel source) {
            return new ProductVM(source);
        }

        @Override
        public ProductVM[] newArray(int size) {
            return new ProductVM[size];
        }
    };
}
