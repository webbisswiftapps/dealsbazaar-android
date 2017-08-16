package apps.webbisswift.dealsbazaar.ui.screens.categorylisting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import javax.inject.Inject;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.base.BaseActivity;
import apps.webbisswift.dealsbazaar.root.App;
import apps.webbisswift.dealsbazaar.ui.adapters.CategoryListAdapter;
import apps.webbisswift.dealsbazaar.ui.screens.categoryproducts.CategoryProductsActivity;
import apps.webbisswift.dealsbazaar.ui.viewmodels.CategoryVM;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by biswas on 15/05/2017.
 */

public class CategoryListingActivity extends BaseActivity implements CategoryListingMVPContract.View, CategoryListAdapter.CategoryListInterface {


    @Inject
    CategoryListingMVPContract.Presenter presenter;

    @BindView(R.id.rootView)
    ViewGroup rootView;

    @BindView(R.id.listProgress)
    AVLoadingIndicatorView listProgress;

    @BindView(R.id.mainVS)
    ViewSwitcher mainVS;

    @BindView(R.id.subCategoriesRV)
    RecyclerView subCatRecyclerView;

    @BindView(R.id.home_banner)
    LinearLayout homeBanner;
    NativeExpressAdView adView;




    CategoryListAdapter mCategoryAdapter;

    private static final String TAG = "CategoryListingActivity";

    String key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_listing_main_layout);

        ((App)getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);

        super.setRootView(rootView);
        super.setPresenter(presenter);

        setupCategoryList();
        setupBanner();


         key = getIntent().getStringExtra("CATEGORY_KEY");
         String title = getIntent().getStringExtra("CATEGORY_TITLE");
        if(key != null && !key.isEmpty()) {
            presenter.getCategories(key);
            setupActionBar(title);
        }else{
            showSnackbar("Invalid Category.");
            finish();
        }



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


    private void setupActionBar(String title){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupCategoryList(){
        /* Deals recyclerview setup */
        mCategoryAdapter = new CategoryListAdapter(this, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        subCatRecyclerView.setLayoutManager(layoutManager);
        subCatRecyclerView.setAdapter(mCategoryAdapter);
        subCatRecyclerView.setHasFixedSize(true);

    }


    @Override
    public void onCategorySelected(CategoryVM category) {
        //showSnackbar("Show Products from category: "+category.getTitle());
        Intent i = new Intent(this, CategoryProductsActivity.class);
        i.putExtra("CAT_MAIN", this.key);
        i.putExtra("CAT_SUB", category.getKey());
        i.putExtra("CAT_TITLE", category.getTitle());
        startActivity(i);
    }

    @Override
    public void showLoading() {
        if(mainVS.getCurrentView().getId() != R.id.listProgress)
            mainVS.showPrevious();
    }

    @Override
    public void showList() {
        if(mainVS.getCurrentView().getId() == R.id.listProgress)
            mainVS.showNext();
    }

    @Override
    public void setCategories(List<CategoryVM> categories) {
        this.mCategoryAdapter.setItems(categories);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adView.destroy();
    }
}
