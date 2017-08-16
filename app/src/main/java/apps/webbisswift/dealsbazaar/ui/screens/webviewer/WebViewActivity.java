package apps.webbisswift.dealsbazaar.ui.screens.webviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import javax.inject.Inject;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.Utils.customview.CTextView;
import apps.webbisswift.dealsbazaar.base.BaseActivity;
import apps.webbisswift.dealsbazaar.root.App;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by biswas on 28/03/2017.
 */

public class WebViewActivity extends BaseActivity implements WebViewActivityMVPContract.View{

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.addressBar)
    CTextView addressBar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    String title;

    @Inject
    WebViewActivityMVPContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);

        ((App)getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);

        super.setRootView(webView);
        super.setPresenter(presenter);


        title = getIntent().getStringExtra("TITLE");

        if(title!=null && !title.isEmpty()){
          addressBar.setText(title);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = getIntent();
        String url = i.getStringExtra("LINK");

        startLoading(url);
    }

    @OnClick(R.id.back_btn)
    public void onBack(){
        finish();
    }

    public void startLoading(String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {

                if(progress < 100){
                    if(progressBar.getVisibility() == View.GONE) progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }

        });


        webView.loadUrl(url);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            if ( webView.canGoBack()) {
                webView.goBack();
                return true;
            }else{
                finish();
                return true;
            }
        }else return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
}
