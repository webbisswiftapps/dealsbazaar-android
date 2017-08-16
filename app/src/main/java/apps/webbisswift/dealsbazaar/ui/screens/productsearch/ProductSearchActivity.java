package apps.webbisswift.dealsbazaar.ui.screens.productsearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.Utils.customview.CAutoCompleteView;
import apps.webbisswift.dealsbazaar.Utils.customview.CTextView;
import apps.webbisswift.dealsbazaar.Utils.customview.ItemSpaceDecorator;
import apps.webbisswift.dealsbazaar.base.BaseActivity;
import apps.webbisswift.dealsbazaar.domain.database.SearchHistory;
import apps.webbisswift.dealsbazaar.root.App;
import apps.webbisswift.dealsbazaar.ui.adapters.DealSectionAdapter;
import apps.webbisswift.dealsbazaar.ui.adapters.ProductGridActionInterface;
import apps.webbisswift.dealsbazaar.ui.adapters.SearchHistoryAdapter;
import apps.webbisswift.dealsbazaar.ui.screens.offerdetails.OfferDetailsActivity;
import apps.webbisswift.dealsbazaar.ui.screens.webviewer.WebViewActivity;
import apps.webbisswift.dealsbazaar.ui.viewmodels.DealSectionVM;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by biswas on 01/04/2017.
 */

public class ProductSearchActivity extends BaseActivity implements ProductSearchMVPContract.View, ProductGridActionInterface {


    private static final String TAG="ProductSearchActivity";

    @BindView(R.id.rootView)
    ViewGroup rootView;

    @BindView(R.id.topSearchBar)
    CAutoCompleteView topSearchBar;

    @Inject
    ProductSearchMVPContract.Presenter presenter;

    @BindView(R.id.dealProgressLayout)
    ViewGroup dealProgressLayout;

    @BindView(R.id.dealsProgress)
    AVLoadingIndicatorView dealsProgress;

    @BindView(R.id.productsRecyclerView)
    RecyclerView productsRecyclerView;


    @BindView(R.id.errorLayout)
    RelativeLayout errorLayout;

    @BindView(R.id.errorMessage)
    CTextView errorMessage;




    DealSectionAdapter productsAdapter;



    @BindView(R.id.toolbar)
    Toolbar toolbar;


    String query;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_search_layout);


        ButterKnife.bind(this);
        ((App)getApplication()).getComponent().inject(this);

        super.setPresenter(this.presenter);
        super.setRootView(this.rootView);

        query = getIntent().getStringExtra("QUERY");
        setupToolbar();
        setupOfferRecyclerView();
        setupSearchBox();


        this.presenter.requestProducts(query);
    }


    private void setupToolbar(){

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        topSearchBar.setText(this.query);

    }

    private void setupOfferRecyclerView(){
        productsRecyclerView.setHasFixedSize(true);
         productsAdapter = new DealSectionAdapter(this, this, 1);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        productsRecyclerView.setAdapter(productsAdapter);
        productsRecyclerView.setNestedScrollingEnabled(false);
        ItemSpaceDecorator dividerItemDecoration = new ItemSpaceDecorator(10);
        productsRecyclerView.addItemDecoration(dividerItemDecoration);

    }

    boolean isLoading = true;




    @Override
    public void showProductsLoading() {

        dealProgressLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideProductsLoading() {
        dealProgressLayout.setVisibility(View.GONE);
    }

    @Override
    public void addSearchResultSection(DealSectionVM product) {
        Log.d(TAG,"New searchresult added to list.. "+product.getDealTitle());
        this.productsAdapter.addRow(product);
    }

    @Override
    public void fillAds() {
        this.productsAdapter.fillAds(productsRecyclerView.getWidth(), false);
    }

    @Override
    public void productsLoadError(String error) {
        Log.d(TAG, "Error while loading products: "+error);
        dealProgressLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        errorMessage.setText(error);

    }



    @OnClick(R.id.back_btn)
    public void onBack(){
        finish();
    }

    private void setupSearchBox(){
        this.topSearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_SEARCH){
                    restartSearch();
                    InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                }
                return true;
            }
        });
    }

    @Override
    public void setSearchHistory(List<SearchHistory> searchHistory) {
        this.topSearchBar.setAdapter(new SearchHistoryAdapter(this, R.layout.search_item, R.id.text, searchHistory));

        this.topSearchBar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                restartSearch();
                InputMethodManager imm = (InputMethodManager) topSearchBar.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(topSearchBar.getWindowToken(), 0);
            }
        });
    }

    @OnClick(R.id.searchBtn)
    public void restartSearch(){
        String newKeyword = this.topSearchBar.getText().toString();
        if(!newKeyword.isEmpty() && !newKeyword.contentEquals(query)){
            this.query = newKeyword;
            this.presenter.cancelPreviousRequest();
            this.productsAdapter.clearAll();
            this.presenter.insertSearchHistory(newKeyword);
            this.showProductsLoading();
            this.presenter.requestProducts(query);
        }
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

    /* Other activity Lifecycle methods */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }else return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.presenter.requestSearchHistory();
    }



}
