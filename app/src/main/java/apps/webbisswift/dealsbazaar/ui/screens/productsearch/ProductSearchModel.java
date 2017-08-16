package apps.webbisswift.dealsbazaar.ui.screens.productsearch;

import java.util.ArrayList;
import java.util.List;

import apps.webbisswift.dealsbazaar.domain.database.SearchHistory;
import apps.webbisswift.dealsbazaar.domain.database.ValidStore;
import apps.webbisswift.dealsbazaar.domain.net.exceptions.NoProductsFoundInOfferException;
import apps.webbisswift.dealsbazaar.domain.net.model.Product;
import apps.webbisswift.dealsbazaar.domain.net.model.Results;
import apps.webbisswift.dealsbazaar.domain.net.model.SearchResponse;
import apps.webbisswift.dealsbazaar.domain.repo.base.SearchHistoryRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.SearchRepository;
import apps.webbisswift.dealsbazaar.domain.repo.base.StoreRepository;
import apps.webbisswift.dealsbazaar.ui.viewmodels.DealSectionVM;
import apps.webbisswift.dealsbazaar.ui.viewmodels.ProductVM;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by biswas on 01/04/2017.
 */

public class ProductSearchModel implements ProductSearchMVPContract.Model {

    private SearchRepository searchRepo;
    private StoreRepository storeRepo;
    private SearchHistoryRepository searchHistoryRepo;



    ProductSearchModel(SearchRepository searchRepo, StoreRepository storeRepo, SearchHistoryRepository searchHistoryRepo) {
        this.searchRepo = searchRepo;
        this.storeRepo = storeRepo;
        this.searchHistoryRepo = searchHistoryRepo;
    }




    @Override
    public Observable<DealSectionVM> loadProducts(final String query) {



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
        return searchRepo.search(query,storePath).flatMap(new Function<SearchResponse, ObservableSource<Results>>() {
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
                    vm.setDealTitle(results.getListingTitle());
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
