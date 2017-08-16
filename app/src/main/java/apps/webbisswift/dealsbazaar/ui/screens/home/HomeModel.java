package apps.webbisswift.dealsbazaar.ui.screens.home;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import apps.webbisswift.dealsbazaar.domain.database.SearchHistory;
import apps.webbisswift.dealsbazaar.domain.database.Slide;
import apps.webbisswift.dealsbazaar.domain.database.ValidStore;
import apps.webbisswift.dealsbazaar.domain.net.exceptions.NoProductsFoundInOfferException;
import apps.webbisswift.dealsbazaar.domain.net.model.DealSection;
import apps.webbisswift.dealsbazaar.domain.net.model.Product;
import apps.webbisswift.dealsbazaar.domain.net.model.Results;
import apps.webbisswift.dealsbazaar.domain.net.model.SearchResponse;
import apps.webbisswift.dealsbazaar.domain.repo.base.DealsRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.SearchHistoryRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.SearchRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.SlidesRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.StoreRepository;
import apps.webbisswift.dealsbazaar.ui.viewmodels.DealSectionVM;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import apps.webbisswift.dealsbazaar.ui.viewmodels.SlideVM;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by biswas on 25/03/2017.
 */

 public class HomeModel implements HomeMVPContract.Model {

    private SlidesRepository slidesRepo;
    private DealsRepository dealsRepo;
    private StoreRepository storeRepo;
    private SearchHistoryRepository searchHistoryRepo;
    private SearchRepository searchRepository;


     HomeModel(SlidesRepository slidesRepo, DealsRepository dealsRepo, StoreRepository storeRepo,
               SearchHistoryRepository searchHistoryRepo,
               SearchRepository searchRepository) {

        this.slidesRepo = slidesRepo;
        this.dealsRepo = dealsRepo;
         this.storeRepo = storeRepo;
         this.searchHistoryRepo = searchHistoryRepo;
         this.searchRepository = searchRepository;
    }



    @Override
    public Observable<SlideVM> loadSlides() {
        return slidesRepo.loadSlides().flatMap(new Function<Slide, ObservableSource<SlideVM>>() {
            @Override
            public ObservableSource<SlideVM> apply(Slide featuredSlide) throws Exception {
                SlideVM vm = new SlideVM();
                vm.setSlideURL(featuredSlide.getmOfferImage());
                vm.setSlideDescription(featuredSlide.getOfferDesc());
                vm.setStoreLogoURL(featuredSlide.getmStoreLogo());
                vm.setOfferURL(featuredSlide.getmOfferLink());
                vm.setOfferStorePath(featuredSlide.getStorePath());
                return Observable.just(vm);
            }
        });
    }


    @Override
    public Observable<DealSectionVM> loadDeals() {

        return storeRepo.getAllStoresAtOnce()
                .flatMap(new Function<List<ValidStore>, ObservableSource<DealSectionVM>>() {
                    @Override
                    public ObservableSource<DealSectionVM> apply(List<ValidStore> validStores) throws Exception {
                        Observable<DealSectionVM> mainObservable = null;
                        for(ValidStore vs: validStores){
                            if(mainObservable == null){
                                mainObservable = loadDeals(vs.getStorePath());
                            }else{
                                mainObservable = Observable.mergeDelayError(mainObservable, loadDeals(vs.getStorePath()));
                            }

                        }
                        return mainObservable;
                    }
                });

    }

    @Override
    public Observable<DealSectionVM> loadDeals(final String storePath) {
        Log.d("LoadDealsHome","Load deals from: "+storePath);
        return dealsRepo.loadDeals(storePath).filter(new Predicate<DealSection>() {
            @Override
            public boolean test(DealSection dealSection) throws Exception {
                return dealSection.getCount() > 0;
            }
        }).flatMap(new Function<DealSection, ObservableSource<DealSectionVM>>() {
            @Override
            public ObservableSource<DealSectionVM> apply(DealSection deal) throws Exception {
                DealSectionVM vm = new DealSectionVM();
                vm.setItemCount(deal.getCount());
                vm.setStoreName(deal.getStore());
                vm.setStoreLogoURL(deal.getStoreLogo());
                vm.setDealTitle(deal.getListingTitle());
                vm.setNextPageURL(null);
                vm.setStorePath(storePath);



                ArrayList<ProductVM> pvms = new ArrayList<ProductVM>();
                for (Product p : deal.getProducts()) {
                    ProductVM pvm = new ProductVM();
                    vm.increaseWeight(10);
                    pvm.setProductName(p.getProductName());
                    pvm.setProductDiscount(p.getProductDiscount());
                    if(p.getProductDiscount() != null && !p.getProductDiscount().isEmpty()){
                        try {
                            int value = Integer.parseInt(p.getProductDiscount().replaceAll("[^\\.0123456789]", ""));
                            pvm.setProductDiscountValue(value);
                            vm.increaseWeight(value);
                            pvm.increaseWeight(value);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    pvm.setProductImageURL(p.getProductImage());

                    if (p.getProductOldPrice() != null) {
                        pvm.setProductPrice(p.getProductNewPrice());
                        pvm.setProductOldPrice(p.getProductOldPrice());
                    } else {
                        pvm.setProductPrice(p.getProductPrice());
                        pvm.setProductOldPrice(null);
                    }
                    pvm.setProductURL(p.getProductLink());
                    pvms.add(pvm);
                }

                vm.setItems(pvms);

                return Observable.just(vm);

            }
        });
    }


    @Override
    public Observable<DealSectionVM> loadRecommendedProducts(final String query) {





        return storeRepo.getAllStoresAtOnce()
                .flatMap(new Function<List<ValidStore>, ObservableSource<DealSectionVM>>() {
                    @Override
                    public ObservableSource<DealSectionVM> apply(List<ValidStore> validStores) throws Exception {
                        Observable<DealSectionVM> mainObservable = null;
                        for(ValidStore vs: validStores){
                            if(mainObservable == null){
                                mainObservable = loadProducts(query, vs.getStorePath());
                            }else{
                                mainObservable = Observable.mergeDelayError(mainObservable, loadProducts(query, vs.getStorePath()));
                            }

                        }
                        return mainObservable;
                    }
                });
    }


    private Observable<DealSectionVM> loadProducts(final String query, final String storePath) {
        return searchRepository.search(query,storePath).flatMap(new Function<SearchResponse, ObservableSource<Results>>() {
            @Override
            public ObservableSource<Results> apply(SearchResponse searchResponse) throws Exception{
                return Observable.fromIterable(searchResponse.getmResults());
            }
        }).filter(new Predicate<Results>() {
            @Override
            public boolean test(Results results) throws Exception {
                return results.getCount() > 0;
            }
        }).flatMap(new Function<Results, ObservableSource<DealSectionVM>>() {
            @Override
            public ObservableSource<DealSectionVM> apply(Results results) throws Exception {

                if(results.getCount()>0) {
                    DealSectionVM vm = new DealSectionVM();
                    vm.setItemCount(results.getCount());
                    vm.setStoreName(results.getStore());
                    vm.setStoreLogoURL(results.getStoreLogo());
                    vm.setDealTitle("Recommended Products");
                    vm.setNextPageURL(results.getNextPageURL());
                    vm.setStorePath(storePath);

                    ArrayList<ProductVM> pvms = new ArrayList<ProductVM>();
                    for (Product p : results.getProducts()) {
                        ProductVM pvm = new ProductVM();
                        vm.increaseWeight(1);
                        pvm.setProductName(p.getProductName());
                        pvm.setProductDiscount(p.getProductDiscount());
                        pvm.setProductDiscount(p.getProductDiscount());
                        if(p.getProductDiscount() != null && !p.getProductDiscount().isEmpty()){
                            try {
                                int value = Integer.parseInt(p.getProductDiscount().replaceAll("[^\\.0123456789]", ""));
                                pvm.setProductDiscountValue(value);
                                vm.increaseWeight(value);
                                //pvm.increaseWeight(value);
                            }catch (Exception e){

                            }
                        }

                        if(pvm.getProductName().toLowerCase().contains(query.toLowerCase())){
                            pvm.increaseWeight(200);
                            vm.increaseWeight(200);
                        }

                        pvm.setProductImageURL(p.getProductImage());

                        if (p.getProductOldPrice() != null) {
                            pvm.setProductPrice(p.getProductNewPrice());
                            pvm.setProductOldPrice(p.getProductOldPrice());
                        } else {
                            pvm.setProductPrice(p.getProductPrice());
                            pvm.setProductOldPrice(null);
                        }
                        pvm.setProductURL(p.getProductLink());
                        pvms.add(pvm);
                    }

                    vm.setItems(pvms);

                    return Observable.just(vm);
                }else return Observable.error(new NoProductsFoundInOfferException());
            }
        });
    }


    @Override
    public Observable<List<SearchHistory>> loadSearchHistory() {
        return this.searchHistoryRepo.getHistory();
    }

    @Override
    public Observable<Boolean> insertSearchHistory(String query)
    {
        return this.searchHistoryRepo.insertHistory(query);
    }
}
