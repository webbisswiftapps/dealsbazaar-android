package apps.webbisswift.dealsbazaar.ui.screens.categoryproducts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import javax.inject.Inject;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.Utils.customview.CTextView;
import apps.webbisswift.dealsbazaar.Utils.customview.ItemSpaceDecorator;
import apps.webbisswift.dealsbazaar.base.BaseActivity;
import apps.webbisswift.dealsbazaar.root.App;
import apps.webbisswift.dealsbazaar.ui.adapters.DealSectionAdapter;
import apps.webbisswift.dealsbazaar.ui.adapters.ProductGridActionInterface;
import apps.webbisswift.dealsbazaar.ui.screens.offerdetails.OfferDetailsActivity;
import apps.webbisswift.dealsbazaar.ui.screens.webviewer.WebViewActivity;
import apps.webbisswift.dealsbazaar.ui.viewmodels.DealSectionVM;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by biswas on 15/05/2017.
 */

public class CategoryProductsActivity extends BaseActivity implements CategoryProductsMVPContract.View, ProductGridActionInterface {


    private static final String TAG="CategoryProductsActivity";

    @BindView(R.id.rootView)
    ViewGroup rootView;



    @Inject
    CategoryProductsMVPContract.Presenter presenter;

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


    String main;
    String sub;
    String title;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_products_main);


        ButterKnife.bind(this);
        ((App)getApplication()).getComponent().inject(this);

        super.setPresenter(this.presenter);
        super.setRootView(this.rootView);

        main = getIntent().getStringExtra("CAT_MAIN");
        sub = getIntent().getStringExtra("CAT_SUB");
        title = getIntent().getStringExtra("CAT_TITLE");

        setupToolbar();
        setupOfferRecyclerView();

        this.presenter.requestProducts(this.main, this.sub);
    }


    private void setupToolbar(){

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.title);
    }

    private void setupOfferRecyclerView(){
        productsRecyclerView.setHasFixedSize(true);
        productsAdapter = new DealSectionAdapter(this, this, 1);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        productsRecyclerView.setAdapter(productsAdapter);

        ItemSpaceDecorator dividerItemDecoration = new ItemSpaceDecorator(10);
        productsRecyclerView.addItemDecoration(dividerItemDecoration);
    }

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
    public void addProductsSection(DealSectionVM product) {
        product.setDealTitle(title);
        this.productsAdapter.addRow(product);
    }

    @Override
    public void fillAds() {
        this.productsAdapter.fillAds(this.productsRecyclerView.getWidth(), false);
    }

    @Override
    public void productsLoadError(String error) {
        dealProgressLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        errorMessage.setText(error);

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
        i.putExtra("TITLE",title);
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

}
