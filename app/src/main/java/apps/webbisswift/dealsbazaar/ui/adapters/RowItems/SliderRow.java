package apps.webbisswift.dealsbazaar.ui.adapters.RowItems;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ViewSwitcher;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.Utils.customview.SlideItemView;
import apps.webbisswift.dealsbazaar.ui.adapters.IRow;
import apps.webbisswift.dealsbazaar.ui.screens.categorylisting.CategoryListingActivity;
import apps.webbisswift.dealsbazaar.ui.viewmodels.SlideVM;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by biswas on 14/08/2017.
 */

public class SliderRow implements IRow{

    private View root;

    @BindView(R.id.slideSwitcher)
    ViewSwitcher slideSwitcher;

    @BindView(R.id.slider)
    SliderLayout slider;


    @BindView(R.id.adHolder)
    FrameLayout adHolder;


    NativeExpressAdView adView;

    private Context c;


    @BindView(R.id.slideProgress)
    AVLoadingIndicatorView slideProgress;

    public SliderRow(Context context){
        this.c = context;
         root = View.inflate(context, R.layout.slider_row_item, null);
        ButterKnife.bind(this, root);

        //prepare slider
         /* Slider setup */
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setPresetTransformer(SliderLayout.Transformer.Stack);
        slider.startAutoCycle();

        //add ad
        addAd();
    }



    @Override
    public int getType() {
        return IRow.TYPE_SLIDER;
    }

    public void addSlide(SlideVM slide, BaseSliderView.OnSliderClickListener listener){
        SlideItemView tv = new SlideItemView(c);
        tv.setScaleType(BaseSliderView.ScaleType.Fit);
        tv.image(slide.getSlideURL()).description(slide.getSlideDescription());
        tv.setTag(slide);
        tv.setOnSliderClickListener(listener);
        slider.addSlider(tv);
    }

    public void showSlidesLoading(){
        if(slideSwitcher.getCurrentView().getId() != R.id.slideProgress)
            slideSwitcher.showPrevious();
    }

    public void hideSlidesLoading(){
        if(slideSwitcher.getCurrentView().getId() != R.id.slider)
            slideSwitcher.showNext();
    }

    public void showSlideError(String error) {
        //showSnackbar(error);
        Log.d("SlideRow", "No slides to show.. hiding slide. Error: "+error);
        slideSwitcher.setVisibility(View.GONE);
    }


    public void addAd(){
        adView = new NativeExpressAdView(c);
        AdSize size = new AdSize(AdSize.FULL_WIDTH, c.getResources().getInteger(R.integer.banner_height_home));
        adView.setAdUnitId(c.getString(R.string.banner_native_home));
        adView.setAdSize(size);



        adHolder.addView(adView);
        this.hideTopBanner();

        /* Request Ads */
        AdRequest request = new AdRequest.Builder()
                .addTestDevice("D97506CE44741D62F39273476ECCCA35")
                .addTestDevice("EFFD38F91EBB8C0FE33D997E91FFB89E")
                .build();
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                hideTopBanner();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                hideTopBanner();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                showTopBanner();
            }
        });

        adView.loadAd(request);
    }

    public void showTopBanner() {
        this.adHolder.setVisibility(View.VISIBLE);

    }

    public void hideTopBanner() {
        this.adHolder.setVisibility(View.GONE);
    }


    public View getRoot(){
        return this.root;
    }

    public void startAutoCycle(){
        this.slider.startAutoCycle();
    }

    public void stopAutoCycle(){
        this.slider.stopAutoCycle();
    }



    @OnClick(R.id.mensFashionG)
    public void openMensFashion(){
        Intent i = new Intent(c, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", c.getString(R.string.category_mens_products));
        i.putExtra("CATEGORY_TITLE", c.getString(R.string.category_mens_products_title));
        c.startActivity(i);
    }

    @OnClick(R.id.womenFashionG)
    public void openWomensFashion(){
        Intent i = new Intent(c, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", c.getString(R.string.category_womens_products));
        i.putExtra("CATEGORY_TITLE", c.getString(R.string.category_womens_products_title));
        c.startActivity(i);
    }

    @OnClick(R.id.mobTabG)
    public void openMobTabSection(){
        Intent i = new Intent(c, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", c.getString(R.string.category_mobile_tablets));
        i.putExtra("CATEGORY_TITLE", c.getString(R.string.category_mobile_tablets_title));
        c.startActivity(i);
    }

    @OnClick(R.id.compTechG)
    public void openComputersSection(){
        Intent i = new Intent(c, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", c.getString(R.string.category_computers));
        i.putExtra("CATEGORY_TITLE", c.getString(R.string.category_computers_title));
        c.startActivity(i);
    }

    @OnClick(R.id.camG)
    public void openCameraSection(){
        Intent i = new Intent(c, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", c.getString(R.string.category_camera));
        i.putExtra("CATEGORY_TITLE", c.getString(R.string.category_camer_title));
        c.startActivity(i);
    }

    @OnClick(R.id.tvEntG)
    public void openTVEntertainmentSection()
    {
        Intent i = new Intent(c, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", c.getString(R.string.category_entertainment));
        i.putExtra("CATEGORY_TITLE", c.getString(R.string.category_entertainment_title));
        c.startActivity(i);
    }

    @OnClick(R.id.homeKitchenG)
    public void homeAndKitchenSection(){
        Intent i = new Intent(c, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", c.getString(R.string.category_home_kitchen));
        i.putExtra("CATEGORY_TITLE", c.getString(R.string.category_home_kitchen_title));
        c.startActivity(i);
    }

    @OnClick(R.id.kidsG)
    public void otherSection(){
        Intent i = new Intent(c, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", c.getString(R.string.category_other));
        i.putExtra("CATEGORY_TITLE", c.getString(R.string.category_other_title));
        c.startActivity(i);
    }

}
