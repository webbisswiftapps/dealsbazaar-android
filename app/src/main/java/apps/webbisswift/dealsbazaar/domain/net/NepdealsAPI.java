package apps.webbisswift.dealsbazaar.domain.net;

import java.util.List;

import apps.webbisswift.dealsbazaar.domain.net.model.DealSection;
import apps.webbisswift.dealsbazaar.domain.net.model.FeaturedSlidesResponse;
import apps.webbisswift.dealsbazaar.domain.net.model.PageResponse;
import apps.webbisswift.dealsbazaar.domain.net.model.SearchResponse;
import apps.webbisswift.dealsbazaar.domain.net.model.Store;
import apps.webbisswift.dealsbazaar.domain.net.model.ValidCategoriesResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by biswas on 25/03/2017.
 */

public interface NepdealsAPI {


    @GET("/api/stores")
    Observable<List<Store>> loadValidStores();

    @GET("/api/featured-offers")
    Observable<FeaturedSlidesResponse> loadFeaturedSlides();

    @GET("/api/featured-offers/{store}")
    Observable<FeaturedSlidesResponse> loadFeaturedSlides(@Path("store") String store);

    @GET("/api/deals")
    Observable<List<DealSection>> loadDeals();

    @GET("/api/deals/{store}")
    Observable<List<DealSection>> loadDeals(@Path("store") String store);

    @GET("/api/page/{store}")
    Observable<PageResponse> loadPage(@Path("store")String store, @Query("url") String url);



    @GET("/api/search/{query}")
    Observable<SearchResponse> searchPage(@Path("query") String query);

    @GET("/api/search/{query}/{store}")
    Observable<SearchResponse> searchPage(@Path("query") String query, @Path("store")String store);

    @GET("/json/{query}")
    Observable<ValidCategoriesResponse> getValidCategories(@Path("query") String query);

    @GET("/api/products/{main}/{sub}/{store}")
    Observable<PageResponse> loadProducts(@Path("main") String main, @Path("sub") String sub, @Path("store") String store);

}
