package apps.webbisswift.dealsbazaar.ui.screens.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import javax.inject.Inject;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.base.BaseActivity;
import apps.webbisswift.dealsbazaar.root.App;
import apps.webbisswift.dealsbazaar.ui.screens.home.HomeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by biswas on 28/03/2017.
 */

public class SplashActivity extends BaseActivity implements SplashMVPContract.View{


    @Inject
    SplashMVPContract.Presenter presenter;

    @BindView(R.id.root)
    ViewGroup rootView;

    private static final String TAG = "SplashActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        ((App)getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);

        super.setRootView(rootView);
        super.setPresenter(presenter);


    }

    @Override
    public void showMain() {
        Intent i = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    /* Other lifecycle methods */
    @Override
    protected void onStart() {
        super.onStart();
        presenter.updateCaches();
    }






}
