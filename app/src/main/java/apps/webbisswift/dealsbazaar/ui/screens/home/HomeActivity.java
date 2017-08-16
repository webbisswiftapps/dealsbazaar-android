package apps.webbisswift.dealsbazaar.ui.screens.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.Utils.Utils;
import apps.webbisswift.dealsbazaar.Utils.customview.CAutoCompleteView;
import apps.webbisswift.dealsbazaar.Utils.customview.CTextView;
import apps.webbisswift.dealsbazaar.Utils.customview.ItemSpaceDecorator;
import apps.webbisswift.dealsbazaar.Utils.customview.SlideItemView;
import apps.webbisswift.dealsbazaar.base.BaseActivity;
import apps.webbisswift.dealsbazaar.domain.database.SearchHistory;
import apps.webbisswift.dealsbazaar.root.App;
import apps.webbisswift.dealsbazaar.ui.adapters.DealSectionAdapter;
import apps.webbisswift.dealsbazaar.ui.adapters.ProductGridActionInterface;
import apps.webbisswift.dealsbazaar.ui.adapters.RowItems.NativeAdVM;
import apps.webbisswift.dealsbazaar.ui.adapters.RowItems.SliderRow;
import apps.webbisswift.dealsbazaar.ui.adapters.SearchHistoryAdapter;
import apps.webbisswift.dealsbazaar.ui.screens.categorylisting.CategoryListingActivity;
import apps.webbisswift.dealsbazaar.ui.screens.offerdetails.OfferDetailsActivity;
import apps.webbisswift.dealsbazaar.ui.screens.productsearch.ProductSearchActivity;
import apps.webbisswift.dealsbazaar.ui.screens.webviewer.WebViewActivity;
import apps.webbisswift.dealsbazaar.ui.viewmodels.DealSectionVM;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import apps.webbisswift.dealsbazaar.ui.viewmodels.SlideVM;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by biswas on 25/03/2017.
 */

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener, HomeMVPContract.View, ProductGridActionInterface {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.content_home)
    ViewGroup rootView;

    @BindView(R.id.dealsRecyclerView)
    RecyclerView dealsRecyclerView;

    @BindView(R.id.dealsProgress)
    AVLoadingIndicatorView progressView;


    ActionBarDrawerToggle actionBarDrawerToggle;

    SliderRow slider = null;

    @Inject
    HomeMVPContract.Presenter presenter;

    private static final String TAG = "HomeActivity";

    private DealSectionAdapter dealsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ((App)getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);

        super.setRootView(rootView);
        super.setPresenter(presenter);


        setupToolbar();
        setupDealsRecyclerView();
        setupSearchBox();

        //add fixed rows
        addFixedRows();

        presenter.requestSlides();
        presenter.requestDeals();

    }



    private void setupToolbar(){
         /* Toolbar & Navigation setup */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.app_name,R.string.app_name);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }


    private void setupDealsRecyclerView(){
         /* Deals recyclerview setup */
        dealsRecyclerView.setHasFixedSize(true);
        dealsAdapter = new DealSectionAdapter(this, this, 2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        dealsRecyclerView.setLayoutManager(layoutManager);
        dealsRecyclerView.setAdapter(dealsAdapter);

        ItemSpaceDecorator dividerItemDecoration = new ItemSpaceDecorator(10);
        dealsRecyclerView.addItemDecoration(dividerItemDecoration);
    }


    private void addFixedRows(){

        slider = new SliderRow(HomeActivity.this);
        dealsAdapter.addRow(slider);

    }

    @BindView(R.id.topSearchBar)
    CAutoCompleteView topSearchBar;

    private void setupSearchBox(){
        this.topSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_SEARCH){
                    search();
                }
                return true;
            }
        });
    }

    @Override
    public void setSearchHistory(List<SearchHistory> searchHistory) {
        SearchHistoryAdapter adapter=new SearchHistoryAdapter(this, R.layout.search_item, R.id.text, searchHistory);
        this.topSearchBar.setAdapter(adapter);
        this.topSearchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                search();
            }
        });

    }

    @OnClick(R.id.searchBtn)
    public void search(){
        String searchQuery = topSearchBar.getText().toString();
        if(!searchQuery.isEmpty()){
            this.presenter.insertSearchHistory(searchQuery);
            Intent i = new Intent(this, ProductSearchActivity.class);
            i.putExtra("QUERY", searchQuery);
            startActivity(i);
            topSearchBar.setText("");
        }
    }

    public void openMensFashion(){
        Intent i = new Intent(this, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", getString(R.string.category_mens_products));
        i.putExtra("CATEGORY_TITLE", getString(R.string.category_mens_products_title));
        startActivity(i);
    }

    public void openWomensFashion(){
        Intent i = new Intent(this, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", getString(R.string.category_womens_products));
        i.putExtra("CATEGORY_TITLE", getString(R.string.category_womens_products_title));
        startActivity(i);
    }

    public void openMobTabSection(){
        Intent i = new Intent(this, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", getString(R.string.category_mobile_tablets));
        i.putExtra("CATEGORY_TITLE", getString(R.string.category_mobile_tablets_title));
        startActivity(i);
    }

    public void openComputersSection(){
        Intent i = new Intent(this, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", getString(R.string.category_computers));
        i.putExtra("CATEGORY_TITLE", getString(R.string.category_computers_title));
        startActivity(i);
    }

    public void openCameraSection(){
        Intent i = new Intent(this, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", getString(R.string.category_camera));
        i.putExtra("CATEGORY_TITLE", getString(R.string.category_camer_title));
        startActivity(i);
    }

    public void openTVEntertainmentSection()
    {
        Intent i = new Intent(this, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", getString(R.string.category_entertainment));
        i.putExtra("CATEGORY_TITLE", getString(R.string.category_entertainment_title));
        startActivity(i);
    }

    public void homeAndKitchenSection(){
        Intent i = new Intent(this, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", getString(R.string.category_home_kitchen));
        i.putExtra("CATEGORY_TITLE", getString(R.string.category_home_kitchen_title));
        startActivity(i);
    }

    public void otherSection(){
        Intent i = new Intent(this, CategoryListingActivity.class);
        i.putExtra("CATEGORY_KEY", getString(R.string.category_other));
        i.putExtra("CATEGORY_TITLE", getString(R.string.category_other_title));
        startActivity(i);
    }


    /* MVP Contract Implementation */


    @Override
    public void showSlidesLoading() {
        this.slider.showSlidesLoading();
    }

    @Override
    public void hideSlidesLoading() {
        this.slider.hideSlidesLoading();
    }





    @Override
    public void addSlide(SlideVM slide) {
        this.slider.addSlide(slide, this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        SlideVM vm = (SlideVM)((SlideItemView) slider).getTag();

        Intent i = new Intent(this, OfferDetailsActivity.class);
        i.putExtra("LINK", vm.getOfferURL());
        i.putExtra("STORE_PATH", vm.getOfferStorePath());
        i.putExtra("TITLE", vm.getSlideDescription());
        i.putExtra("IMAGE", vm.getSlideURL());
        i.putExtra("LOGO", vm.getStoreLogoURL());

        startActivity(i);
    }

    @Override
    public void showSlideError(String error) {
        Log.d(TAG, "No slides to show.. hiding slide. Error: "+error);
    }

    @Override
    public void showDealsLoading() {
        progressView.show();
    }

    @Override
    public void hideDealsLoading() {
        progressView.hide();
    }

    @Override
    public void addDealSection(DealSectionVM dealsection) {
        this.dealsAdapter.addRow(dealsection);
    }

    @Override
    public void fillAdsInList() {
        this.dealsAdapter.fillAds(this.dealsRecyclerView.getWidth(), true);
    }

    @Override
    public void dealLoadError(String error) {

    }

    /* Grid action Interface */

    @Override
    public void onProductClicked(String productURL, String title) {
        Intent i = new Intent(this, WebViewActivity.class);
        i.putExtra("LINK", productURL);
        i.putExtra("TITLE", title);
        startActivity(i);
    }

    @Override
    public void onViewAllClicked(DealSectionVM section) {
        Intent i = new Intent(this, OfferDetailsActivity.class);
        i.putExtra("TITLE",section.getDealTitle());
        i.putExtra("STORE_PATH",section.getStorePath());
        i.putExtra("LINK", section.getNextPageURL());
        i.putExtra("IMAGE", "");
        i.putExtra("LOGO",section.getStoreLogoURL());

        ArrayList<ProductVM> products = new ArrayList<>(section.getItems());
        i.putParcelableArrayListExtra("LOADED_PRODUCTS", products);
        startActivity(i);
    }

    /* Other lifecycle methods */

    @Override
    protected void onStart() {
        super.onStart();
        this.slider.startAutoCycle();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_men) {
            openMensFashion();
        } else if (id == R.id.nav_women) {
            openWomensFashion();
        } else if (id == R.id.nav_mobiles) {
            openMobTabSection();
        } else if (id == R.id.nav_computers) {
            openComputersSection();
        } else if (id == R.id.nav_entertainment) {
            openTVEntertainmentSection();
        } else if (id == R.id.nav_camera) {
            openCameraSection();
        } else if (id == R.id.nav_home) {
            homeAndKitchenSection();
        } else if (id == R.id.nav_other) {
            otherSection();
        }else if(id == R.id.nav_share){
            shareApp();
        }else if(id == R.id.nav_more_apps){
            moreApps();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_disclaimer:
                showDisclaimer();
                return true;
            case R.id.request_feature:
                 featureRequest();
                return true;
            case R.id.send_feedback:
                feedback();
                return true;
            default:
                return false;
        }
    }


    private void showDisclaimer(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(LayoutInflater.from(this).inflate(R.layout.layout_disclaimer, null));
        builder.create().show();
    }

    private void featureRequest(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","webbisswiftapps@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feature Request for Deals Bazaar App");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void feedback(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","webbisswiftapps@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Deals Bazaar App");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


    private void shareApp(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=apps.webbisswift.dealsbazaar");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void moreApps(){
        String url = "https://play.google.com/store/apps/developer?id=Webbisswift+Applications";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.presenter.requestSearchHistory();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.slider.stopAutoCycle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
