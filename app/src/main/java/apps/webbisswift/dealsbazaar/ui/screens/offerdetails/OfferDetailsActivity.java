package apps.webbisswift.dealsbazaar.ui.screens.offerdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import javax.inject.Inject;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.Utils.customview.CTextView;
import apps.webbisswift.dealsbazaar.base.BaseActivity;
import apps.webbisswift.dealsbazaar.root.App;
import apps.webbisswift.dealsbazaar.ui.adapters.OfferGridAdapter;
import apps.webbisswift.dealsbazaar.ui.screens.webviewer.WebViewActivity;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by biswas on 28/03/2017.
 */

public class OfferDetailsActivity extends BaseActivity implements OfferDetailsMVPContract.View, AppBarLayout.OnOffsetChangedListener , OfferGridAdapter.OnLoadMoreListener{


    private static final String TAG="OfferDetailsActivity";

    CollapsingToolbarLayout collapsingToolbarLayout;

    @Inject
    OfferDetailsMVPContract.Presenter presenter;

    @BindView(R.id.offerProgress)
    AVLoadingIndicatorView offerProgress;

    @BindView(R.id.bottomProgress)
    AVLoadingIndicatorView bottomProgress;

    @BindView(R.id.offersRecyclerView)
    RecyclerView offersRecyclerView;

    @BindView(R.id.storeLogo)
    ImageView storeLogo;



    OfferGridAdapter offersAdapter;

    @BindView(R.id.main_appbar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_linearlayout_title)
    RelativeLayout mTitleContainer;

    @BindView(R.id.errorLayout)
    RelativeLayout errorLayout;

    @BindView(R.id.errorMessage)
    CTextView errorMessage;

    @BindView(R.id.home_banner)
    LinearLayout homeBanner;

    NativeExpressAdView adView;


    ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);


        ButterKnife.bind(this);
        ((App)getApplication()).getComponent().inject(this);

        super.setPresenter(this.presenter);
        super.setRootView(collapsingToolbarLayout);


        setupBanner();
        setupToolbar();
        setupOfferRecyclerView();

        String url = getIntent().getStringExtra("LINK");
        String storePath = getIntent().getStringExtra("STORE_PATH");

        if(url != null && !url.isEmpty())
            this.presenter.requestProducts(url, storePath);
        else this.hideProductsLoading();


        ArrayList<ProductVM> initial = getIntent().getParcelableArrayListExtra("LOADED_PRODUCTS");
        this.presenter.setInitialProducts(initial);


    }

    private void setupBanner(){
        adView = new NativeExpressAdView(this);
        AdSize size = new AdSize(AdSize.FULL_WIDTH, getResources().getInteger(R.integer.banner_height_home));
        adView.setAdUnitId(getString(R.string.banner_native_home));
        adView.setAdSize(size);



        homeBanner.addView(adView);
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



    private void setupToolbar(){

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);

        ImageView headerImage = (ImageView) collapsingToolbarLayout.findViewById(R.id.headerImage);

        String image = getIntent().getStringExtra("IMAGE");
        String logo = getIntent().getStringExtra("LOGO");

        if(image!=null && !image.isEmpty()) {
            Glide.with(this)
                    .load(image)
                    .override(320, 240)
                    .into(headerImage);
        }

        Glide.with(this).load(logo).into(storeLogo);


        String title = getIntent().getStringExtra("TITLE");
        if(title==null || title.isEmpty())
            title = "Offer";

        actionBar.setTitle(title);


        mAppBarLayout.addOnOffsetChangedListener(this);
        startAlphaAnimation(storeLogo, 0, View.VISIBLE);
    }

    private void setupOfferRecyclerView(){
         /* Deals recyclerview setup */
        offersAdapter = new OfferGridAdapter(this, this);

        int items = getResources().getInteger(R.integer.product_grid_items);
        GridLayoutManager lm = new GridLayoutManager(this, items);
        offersRecyclerView.setLayoutManager(lm);

        offersRecyclerView.setAdapter(offersAdapter);
        offersRecyclerView.setNestedScrollingEnabled(true);

    }

    boolean isLoading = true;

    @Override
    public void onLoadMore() {
        if(!isLoading && presenter.canRequestMore()) {
            showProductsLoading(true);
            presenter.requestMore();
        }else{
            offersAdapter.setLoadMoreEnabled(false);
        }
    }


    @Override
    public void showTopBanner() {
        homeBanner.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTopBanner() {
        homeBanner.setVisibility(View.GONE);
    }

    @Override
    public void showProductsLoading(boolean more) {
        isLoading = true;
        if(!more)
            offerProgress.smoothToShow();
        else bottomProgress.smoothToShow();
        offersAdapter.setLoadMoreEnabled(false);

    }

    @Override
    public void stopLoadingMore() {
        isLoading= false;
        bottomProgress.smoothToHide();
        offersAdapter.setLoadMoreEnabled(false);
    }

    @Override
    public void hideProductsLoading() {
        isLoading = false;
        offerProgress.smoothToHide();
        bottomProgress.smoothToHide();
        offersAdapter.setLoadMoreEnabled(true);
    }

    @Override
    public void addProduct(ProductVM product) {
        this.offersAdapter.addProduct(product);
    }


    @Override
    public void productsLoadError(String error) {
        //showSnackbar("Error while loading deals: "+error);
        showSnackbar("Error while loading: "+error);
        offerProgress.smoothToHide();
        bottomProgress.smoothToHide();
        offersAdapter.setLoadMoreEnabled(false);
        errorLayout.setVisibility(View.VISIBLE);
        errorMessage.setText(error);
    }

    @Override
    public void loadInWebView(String url) {
        Intent i = new Intent(this, WebViewActivity.class);
        i.putExtra("LINK", url);
        i.putExtra("TITLE", url);
        startActivity(i);
        finish();
    }



    /* Other activity Lifecycle methods */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }else return false;
    }



    /* Toolbar Offset */

    private static final float PERCENTAGE_HIDE_LOGO  = 0.65f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsLogoVisible          = true;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        handleLogoVisibility(percentage);
    }


    private void handleLogoVisibility(float percentage) {
        if(percentage >= PERCENTAGE_HIDE_LOGO){
            if(mIsLogoVisible){
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.GONE);
                mIsLogoVisible = false;
            }
        }else{
            if(!mIsLogoVisible){
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsLogoVisible = true;

            }
        }
    }




    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        this.adView.destroy();
        super.onDestroy();
    }
}
