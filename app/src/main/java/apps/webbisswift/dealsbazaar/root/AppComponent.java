package apps.webbisswift.dealsbazaar.root;

import javax.inject.Singleton;

import apps.webbisswift.dealsbazaar.domain.net.NepdealsAPIModule;
import apps.webbisswift.dealsbazaar.domain.prefs.SyncStateModule;
import apps.webbisswift.dealsbazaar.domain.prefs.ValidCategoryCacheModule;
import apps.webbisswift.dealsbazaar.ui.screens.categorylisting.CategoryListingActivity;
import apps.webbisswift.dealsbazaar.ui.screens.categorylisting.CategoryListingModule;
import apps.webbisswift.dealsbazaar.ui.screens.categoryproducts.CategoryProductsActivity;
import apps.webbisswift.dealsbazaar.ui.screens.categoryproducts.CategoryProductsModule;
import apps.webbisswift.dealsbazaar.ui.screens.home.HomeActivity;
import apps.webbisswift.dealsbazaar.ui.screens.home.HomeModule;
import apps.webbisswift.dealsbazaar.ui.screens.offerdetails.OfferDetailsActivity;
import apps.webbisswift.dealsbazaar.ui.screens.offerdetails.OfferDetailsModule;
import apps.webbisswift.dealsbazaar.ui.screens.productsearch.ProductSearchActivity;
import apps.webbisswift.dealsbazaar.ui.screens.productsearch.ProductSearchModule;
import apps.webbisswift.dealsbazaar.ui.screens.splash.SplashActivity;
import apps.webbisswift.dealsbazaar.ui.screens.splash.SplashModule;
import apps.webbisswift.dealsbazaar.ui.screens.webviewer.WebViewActivity;
import apps.webbisswift.dealsbazaar.ui.screens.webviewer.WebViewModule;
import dagger.Component;

/**
 * Created by biswas on 25/03/2017.
 */

@Singleton
@Component(modules = {AppModule.class,
        SyncStateModule.class,
        NepdealsAPIModule.class,
        HomeModule.class,
        SplashModule.class,
        WebViewModule.class,
        CategoryListingModule.class,
        OfferDetailsModule.class,
        ProductSearchModule.class,
        ValidCategoryCacheModule.class,
        CategoryProductsModule.class
})
public interface AppComponent {

    public void inject(HomeActivity target);
    public void inject(SplashActivity target);
    public void inject(WebViewActivity target);
    public void inject(OfferDetailsActivity target);
    public void inject(ProductSearchActivity target);
    public void inject(CategoryListingActivity target);
    public void inject(CategoryProductsActivity target);
}