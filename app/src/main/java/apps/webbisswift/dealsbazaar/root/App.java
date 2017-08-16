package apps.webbisswift.dealsbazaar.root;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.messaging.FirebaseMessaging;
import com.orm.SugarContext;
import com.squareup.leakcanary.LeakCanary;

import apps.webbisswift.dealsbazaar.R;
import apps.webbisswift.dealsbazaar.domain.net.NepdealsAPIModule;
import apps.webbisswift.dealsbazaar.domain.prefs.SyncStateModule;
import apps.webbisswift.dealsbazaar.domain.prefs.ValidCategoryCacheModule;
import apps.webbisswift.dealsbazaar.ui.screens.categorylisting.CategoryListingModule;
import apps.webbisswift.dealsbazaar.ui.screens.categoryproducts.CategoryProductsModule;
import apps.webbisswift.dealsbazaar.ui.screens.home.HomeModule;
import apps.webbisswift.dealsbazaar.ui.screens.offerdetails.OfferDetailsModule;
import apps.webbisswift.dealsbazaar.ui.screens.productsearch.ProductSearchModule;
import apps.webbisswift.dealsbazaar.ui.screens.splash.SplashModule;
import apps.webbisswift.dealsbazaar.ui.screens.webviewer.WebViewModule;

/**
 * Created by biswas on 25/03/2017.
 */
public class App extends Application {


    AppComponent component;


    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        Stetho.initializeWithDefaults(this);
        SugarContext.init(this);
        FirebaseMessaging.getInstance().subscribeToTopic("dealsbazaar");
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .nepdealsAPIModule(new NepdealsAPIModule())
                .homeModule(new HomeModule())
                .splashModule(new SplashModule())
                .syncStateModule(new SyncStateModule())
                .webViewModule(new WebViewModule())
                .productSearchModule(new ProductSearchModule())
                .offerDetailsModule(new OfferDetailsModule())
                .validCategoryCacheModule(new ValidCategoryCacheModule())
                .categoryListingModule(new CategoryListingModule())
                .categoryProductsModule(new CategoryProductsModule())
                .build();

        MobileAds.initialize(this, getString(R.string.admob_appid));

    }

    public AppComponent getComponent() {
        return component;
    }


    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }
}